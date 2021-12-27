package dev.mqzn.lib.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;

public class Context implements CommandContext{


    private final CommandSender sender;
    private final ArrayList<String> args;

    Context(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = collectArgs(args);

    }

    private ArrayList<String> collectArgs(String[] args) {

        ArrayList<String> toCollect = new ArrayList<>();
        Collections.addAll(toCollect, args);
        return toCollect;
    }


    @Override
    public CommandSender getSender() {
        return sender;
    }

    @Override
    public ArrayList<String> getContextArgs() {
        return args;
    }


}
