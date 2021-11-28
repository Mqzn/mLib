package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.main.SubCommand;

public class FirstSub extends SubCommand
{



    public FirstSub() {
        super("first", 0);
    }

    @Override
    public String getPermission() {
        return "test.first.perm";
    }

    @Override
    public String getDescription() {
        return "first sub";
    }

    @Override
    public void setRequirements() {
        this.addRequirement(new TrySub());
    }




}
