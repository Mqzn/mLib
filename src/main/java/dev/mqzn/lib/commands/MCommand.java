package dev.mqzn.lib.commands;

import dev.mqzn.lib.commands.exceptions.IllegalSyntaxProvidedException;
import dev.mqzn.lib.mLib;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public abstract class MCommand extends Command {

    private final ArrayList<CommandSyntax> syntaxes = new ArrayList<>();
    private BiConsumer<CommandSender, ArrayList<String>> defaultActions = null;
    private CommandContext context;

    private String permission = null;
    public MCommand(String name) {
        super(name);
        this.setInfo();
    }

    public MCommand(String name, String permission) {
        super(name);
        this.permission = permission;
        this.setInfo();
    }

    boolean hasDefaultExecutor() {
        return defaultActions != null;
    }

    protected void setDefaultActions(BiConsumer<CommandSender, ArrayList<String>> actions) {
        this.defaultActions = actions;
    }

    public BiConsumer<CommandSender, ArrayList<String>> getDefaultActions() {
        return defaultActions;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    public abstract void setInfo();

    protected void addAction(String syntax, BiConsumer<CommandSender, ArrayList<String>> actions, boolean allowConsole) throws IllegalSyntaxProvidedException {

        if(syntax.startsWith("/") || syntax.startsWith(this.getName())){
            throw new IllegalSyntaxProvidedException(this, syntax);
        }

        CommandSyntax commandSyntax = new CommandSyntax(syntax, allowConsole, actions);

        if(!syntax.contains(" ")) {
            commandSyntax.setArg(new CommandParameter(syntax, 0, ParameterType.fromArg(syntax)));
            this.syntaxes.add(commandSyntax);
            return;
        }

        // example:
        //original usage: /command test <Value>
        String[] split = syntax.split(" ");
        for (int i = 0, splitLength = split.length; i < splitLength; i++) {
            String arg = split[i];
            ParameterType type = ParameterType.fromArg(arg);
            commandSyntax.setArg(new CommandParameter(arg, i, type));
        }

        this.syntaxes.add(commandSyntax);
    }

    private BiConsumer<CommandSender, ArrayList<String>> searchForActions(CommandSender sender) {
        for (CommandSyntax cs : syntaxes) {

            if(cs.matchesArgs(context.getContextArgs())) {
                if(!(sender instanceof Player) && !cs.allowsConsole()) {
                    sender.sendMessage(Translator.color("Only players can do that !"));
                    break;
                }
                return cs.getActions();
            }
        }

        return null;
    }


    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        context = new Context<>(this, sender, args);
        Bukkit.getScheduler().runTaskAsynchronously(mLib.INSTANCE, this::run);
        return true;
    }


    void run() {
        BiConsumer<CommandSender, ArrayList<String>> toRun = this.searchForActions(context.getSender());

        if(context.getContextArgs().isEmpty() || toRun == null) {
            if(this.hasDefaultExecutor()) {
                this.getDefaultActions().accept(context.getSender(), context.getContextArgs());
                return;
            }

            for(CommandSyntax syntax : syntaxes) {
                context.getSender().sendMessage(Translator.color("&c" + syntax.getUsage(this)));
            }
            return;
        }

        toRun.accept(context.getSender(), context.getContextArgs());
    }

}
