package dev.mqzn.lib.commands.api.main;

import com.google.common.base.Objects;
import dev.mqzn.lib.commands.CommandManager;
import dev.mqzn.lib.commands.resolvers.ArgsResolver;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import java.util.*;
import java.util.logging.Level;

public abstract class SubCommand extends Requirement {

    private final String name;
    private final int position;

    private final Set<Requirement> requirements;
    private final Set<SubCommand> children;

    public SubCommand(String name, int position) {
        super((args)-> position >= 0 && position <= args.size()-1
                && args.get(position) != null
                && args.get(position).getArgument().equalsIgnoreCase(name));

        this.name = name;
        this.position = position;

        requirements = new LinkedHashSet<>();
        children = new LinkedHashSet<>();
        this.setRequirements();

        this.collectUsageArgs();

    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public Set<SubCommand> getChildren() {
        return children;
    }

    public boolean isParent() {
        return !children.isEmpty();
    }

    public void sendUsage(MCommand command, CommandSender sender) {
        for(Requirement reqs : this.getRequirements()) {
            sender.sendMessage(reqs.getUsage(command, this));
        }
        for(SubCommand child : this.getChildren()) {
            sender.sendMessage(child.getUsage(command, this));
        }
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public abstract String getPermission();

    public abstract String getDescription();

    public abstract void setRequirements();

    protected void addRequirement(Criteria criteria, Executor executor, UsageArg... args) {
        Requirement requirement = Requirement.of(criteria, executor);
        for(UsageArg arg : args) {
            requirement.setArg(arg);
        }
        this.addRequirement(requirement);
    }

    protected void addChildren(SubCommand... children) {
        this.children.addAll(Arrays.asList(children));
    }

    protected void addRequirement(Requirement requirement) {
        if(requirement instanceof  SubCommand)
            children.add((SubCommand)requirement);
        else
            requirements.add(requirement);

    }


    private void collectUsageArgs() {
        requirements.forEach(rq -> rq.getArgParses().forEach(this::setArg));
        children.forEach(c -> c.getArgParses().forEach(this::setArg));
    }

    @Override
    public boolean execute(MCommand mCommand, CommandSender sender, List<CommandArg> args) {

        assert this.getCriteria().test(args);

        Optional<Requirement> optional = getRequirementUsed(requirements, args);
        if(optional.isPresent()) {
            return optional.get().execute(mCommand, sender, args);
        }

        executeChildren(mCommand, this, sender, args);
        return true;
    }



    static Optional<Requirement> getRequirementUsed(Set<? extends Requirement> storedReqs, List<CommandArg> args) {

        Requirement found = null;
        ArgsResolver<Requirement> resolver = CommandManager.getDefaultResolver();
        for (Requirement requirement : storedReqs) {
            if (!requirement.getCriteria().test(args) || resolver.argsUnMatched(requirement, args)) continue;
            found = requirement;
            break;
        }

        return Optional.ofNullable(found);
    }

    private static Optional<SubCommand> getChildUsed(SubCommand parent, List<CommandArg> args) {

        Set<? extends SubCommand> children = parent.getChildren();

        int pos = parent.getPosition();
        assert pos > 0 &&
                pos <= args.size()-1;

        SubCommand childSafe = null;

        args_lookup: for (int start = pos+1; start <= args.size()-1; start++) {

            for(SubCommand child : children) {
                if(!child.getCriteria().test(args)) continue;
                childSafe = child;
                break args_lookup;
            }
        }

        return Optional.ofNullable(childSafe);
    }

    static void executeChildren(MCommand command, SubCommand subCommand, CommandSender sender, List<CommandArg> args) {

        Optional<Requirement> subReqSafe = SubCommand.getRequirementUsed(subCommand.getChildren(), args);

        if(!subReqSafe.isPresent()) {
            Bukkit.getLogger().log(Level.INFO, "NO child found of SubCommand '" + subCommand.getName() + "'");

            Optional<Requirement> finalExecutor = getRequirementUsed(subCommand.getRequirements(), args);
            if(!finalExecutor.isPresent()) {
                command.sendAllUsages(sender);
            }else {
                finalExecutor.get().execute(command, sender, args);
            }
            return;
        }

        Requirement found = subReqSafe.get();
        if(found.hasActions()) {
            Bukkit.getLogger().log(Level.INFO, "Requirement has some actions !, executing them...");
            found.execute(command, sender, args);
            return;
        }

        //NO ACTIONS  and it's not a subcommand!
        if(!(found instanceof SubCommand) || !subCommand.isParent()) {
            Translator.log("Found requirement may not be a child subcommand !");
            subCommand.sendUsage(command, sender);
            return;
        }

        SubCommand foundSub = (SubCommand)found;
        if(!foundSub.isParent()) {
            Translator.log("found subcommand is NOT a parent !");
            foundSub.sendUsage(command, sender);
            return;
        }


        Optional<SubCommand> child = SubCommand.getChildUsed(foundSub, args);
        if(!child.isPresent()) {
            Translator.log("No children found");
            subCommand.sendUsage(command, sender);
            return;
        }

        executeChildren(command, child.get(), sender, args);

    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubCommand)) return false;
        if (!super.equals(o)) return false;
        SubCommand that = (SubCommand) o;
        return getName().equals(that.getName()) && getPosition() == that.getPosition() &&
                Objects.equal(getRequirements(), that.getRequirements()) &&
                Objects.equal(getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getName(),
                getPosition(), getRequirements(), getChildren());
    }


}
