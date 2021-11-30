package dev.mqzn.lib.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public interface CommandContext {


    CommandSender getSender();

    ArrayList<String> getContextArgs();


    MCommand getCommand();

    default String toText() {
        StringBuilder builder = new StringBuilder();
        for (String arg : getContextArgs()) {
            builder.append(arg).append(" ");
        }
        return builder.toString().trim();
    }

}
