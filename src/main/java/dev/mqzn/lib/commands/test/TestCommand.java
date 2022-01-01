package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.MCommand;
import org.bukkit.entity.Player;

public class TestCommand extends MCommand {

    public TestCommand() {
        super("test", "perm.test", false, "test2");
    }

    @Override
    public void setInfo() {

        this.setDefaultActions(((sender, args) -> {
            Player player = (Player)sender;
            //DO SHIT HERE
        }));

    }

}
