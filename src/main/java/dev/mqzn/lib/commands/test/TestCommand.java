package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.main.MCommand;

public class TestCommand extends MCommand {
    public TestCommand() {
        super("test", "test.perm",
                "Just a test",
                "&c/Test first try second",
                true, "exam");
    }

    @Override
    public void setRequirements() {


        this.addRequirement(new FirstSub());

    }
}
