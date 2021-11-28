package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.main.SubCommand;

public class TrySub extends SubCommand {

    public TrySub() {
        super("try", 1);
    }

    @Override
    public String getPermission() {
        return "test.first.try.perm";
    }

    @Override
    public String getDescription() {
        return "Try sub";
    }

    @Override
    public void setRequirements() {
        this.addChildren(new SecondSub());
    }


}
