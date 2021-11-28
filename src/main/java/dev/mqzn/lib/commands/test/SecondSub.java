package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.main.SubCommand;
import dev.mqzn.lib.commands.api.main.UsageArg;
import dev.mqzn.lib.utils.Translator;

public class SecondSub extends SubCommand {

    public SecondSub() {
        super("second", 2);
    }

    @Override
    public String getPermission() {
        return "test.first.try.second.perm";
    }

    @Override
    public String getDescription() {
        return "Second sub";
    }

    @Override
    public void setRequirements() {
        this.addRequirement((args) -> args.size() == 3, (sender, args)
                -> sender.sendMessage(Translator.color("&aIT WORKS")),
                new UsageArg("first", 0, String.class, UsageArg.ArgumentType.NONE),
                new UsageArg("try", 1, String.class, UsageArg.ArgumentType.NONE),
                new UsageArg("second", 2, String.class, UsageArg.ArgumentType.NONE));
    }
}
