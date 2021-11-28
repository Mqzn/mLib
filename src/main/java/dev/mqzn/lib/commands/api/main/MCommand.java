package dev.mqzn.lib.commands.api.main;

import dev.mqzn.lib.commands.CommandManager;
import dev.mqzn.lib.commands.api.parsers.impl.ArgumentParser;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.logging.Level;

public abstract class MCommand extends Command {

    static String NO_PERMISSION, ONLY_PLAYER;

    static {
        NO_PERMISSION = Translator.color("&cAccess denied to the command");
        ONLY_PLAYER = Translator.color("&cOnly players can do this !");
    }

    private final String permission;
    private final boolean allowConsole;
    private final Set<Requirement> requirements;

    public MCommand(String name, String permission, String desc, String usage, boolean allowConsole, String... aliases) {
        super(name, Translator.color(desc), usage, Arrays.asList(aliases));
        this.permission = permission;
        this.allowConsole = allowConsole;
        requirements = new LinkedHashSet<>();
        this.setRequirements();
    }

    public MCommand(String name, String desc, String usage, boolean allowConsole, String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.permission = null;
        this.allowConsole = allowConsole;

        requirements = new HashSet<>();
        this.setRequirements();
    }


    @Override
    public String getPermission() {
        return this.permission == null ? "command."
                + this.getName().toLowerCase() : this.permission;
    }


    public abstract void setRequirements();

    public boolean execute(CommandSender sender, String label, String[] args) {

        if(!allowConsole && !(sender instanceof Player)) {
            sender.sendMessage(ONLY_PLAYER);
            return true;
        }

        if(!sender.hasPermission(this.getPermission())) {
            sender.sendMessage(NO_PERMISSION);
            return true;
        }
        execute(sender,collectArgs(args));
        return true;
    }

    private List<CommandArg> collectArgs(String[] args) {
        List<CommandArg> toCollect = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            ArgumentParser<?> parser = CommandManager.getInstance().getArgumentParser(CommandManager.getInstance().getClazzType(arg));
            toCollect.add(new CommandArg(i, parser.parse(arg), arg));
        }
        return toCollect;
    }


    public void execute(CommandSender sender, List<CommandArg> args) {
        args.removeIf(Objects::isNull);
        Optional<Requirement> found = SubCommand.getRequirementUsed(requirements, args);

        if (!found.isPresent()) {
            this.sendAllUsages(sender);
            Bukkit.getLogger().log(Level.WARNING, "NO USAGES FOUND");
            return;
        }

        Requirement req = found.get();

        if (req.getExecutor() != null && !(req instanceof SubCommand)) {
            req.execute(this, sender, args);
            return;
        }

        SubCommand subCommand = (SubCommand) req;
        Bukkit.getLogger().log(Level.INFO, "FOUND SubCommand: " + subCommand.getName());
        if (!sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(NO_PERMISSION);
            return;
        }

        subCommand.execute(this, sender, args);
    }



    protected void addRequirement(Requirement.Criteria criteria, Requirement.Executor executor, UsageArg... usageArgs) {
        Requirement requirement = Requirement.of(criteria, executor);
        for(UsageArg arg : usageArgs) {
            requirement.setArg(arg);
        }
        requirements.add(requirement);
    }

    protected void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public void sendAllUsages(CommandSender sender) {

        for(Requirement rq : requirements)
            if (rq instanceof SubCommand)
                ((SubCommand) rq).sendUsage(this, sender);
            else
                sender.sendMessage(rq.getUsage(this));

    }

}
