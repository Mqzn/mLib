package dev.mqzn.lib.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public interface CommandContext {

    CommandSender getSender();
    ArrayList<String> getContextArgs();

}
