package dev.mqzn.lib.commands.resolvers.impl;

import dev.mqzn.lib.commands.CommandManager;
import dev.mqzn.lib.commands.api.main.CommandArg;
import dev.mqzn.lib.commands.api.main.Requirement;
import dev.mqzn.lib.commands.api.main.UsageArg;
import dev.mqzn.lib.commands.api.parsers.impl.ArgumentParser;
import dev.mqzn.lib.commands.resolvers.ArgsResolver;
import java.util.List;

public class DefaultResolver<R extends Requirement> implements ArgsResolver<R> {

    @Override
    public boolean argsUnMatched(R requirement, List<CommandArg> args) {

        if(requirement.getArgParses().isEmpty()) {
            return false;
        }

        for (UsageArg argEntry : requirement.getArgParses()) {

            CommandArg arg = args.get(argEntry.getPosition());
            ArgumentParser<?> parser = CommandManager.getInstance().getArgumentParser(argEntry.getTypeClass());
            if (!parser.matches(arg.getArgument())) {
                return true;
            }
        }

        return false;
    }

}
