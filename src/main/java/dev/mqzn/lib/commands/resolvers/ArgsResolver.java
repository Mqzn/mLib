package dev.mqzn.lib.commands.resolvers;

import dev.mqzn.lib.commands.api.main.CommandArg;
import dev.mqzn.lib.commands.api.main.Requirement;

import java.util.List;

public interface ArgsResolver<R extends Requirement> {


    boolean argsUnMatched(R requirement, List<CommandArg> args);


}
