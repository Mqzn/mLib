package dev.mqzn.lib.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;

public class Context<C extends MCommand> implements CommandContext{


    private final C command;
    private final CommandSender sender;
    private final ArrayList<String> args;

    Context(C command, CommandSender sender, String[] args) {

        this.command = command;
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

    @Override
    public C getCommand() {
        return command;
    }


}
