package dev.mqzn.lib.commands;

import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class CommandSyntax {

    private final String originalSyntax;
    private final ConcurrentHashMap<Integer, CommandParameter> params = new ConcurrentHashMap<>(15);
    private final BiConsumer<CommandSender, ArrayList<String>> actions;
    private final boolean allowConsole;

    public CommandSyntax(String originalSyntax, BiConsumer<CommandSender, ArrayList<String>> actions) {
        this.originalSyntax = originalSyntax;
        allowConsole = true;
        this.actions = actions;
    }

    public CommandSyntax(String originalSyntax, boolean allowConsole, BiConsumer<CommandSender, ArrayList<String>> actions) {
        this.originalSyntax = originalSyntax;
        this.allowConsole = allowConsole;
        this.actions = actions;
    }

    public BiConsumer<CommandSender, ArrayList<String>> getActions() {
        return actions;
    }

    public ConcurrentHashMap<Integer, CommandParameter> getParams() {
        return params;
    }

    public void setArg(CommandParameter parameter) {
        params.put(parameter.getIndex(), parameter);
    }

    public void removeArg(int index) {
        params.remove(index);
    }


    public boolean matchesArgs(ArrayList<String> args) {

        int endIndex = args.size();
        if(params.size() != args.size()) {

            CommandParameter var = getVarArg();
            if(var == null) {
                return false;
            }

            endIndex = var.getIndex()-1;
        }

        boolean argsMatches = true;

        for (int pos = 0; pos < endIndex; pos++) {

            CommandParameter parameter = params.get(pos);
            if(parameter == null) {
                argsMatches = false;
                break;
            }
            if(parameter.getParameterType() == ParameterType.NONE) {
                if(!parameter.getParameter().trim().equalsIgnoreCase(args.get(pos).trim())) {
                    argsMatches = false;
                    break;
                }
            }

        }

        return argsMatches;
    }

    boolean isVarArg(CommandParameter parameter) {
        return parameter.getParameter().contains("...") || parameter.getParameter().contains("..");
    }

    public CommandParameter getVarArg() {
        for (CommandParameter param : params.values()) {
            if (isVarArg(param)) {
                return param;
            }
        }
        return null;
    }
    public boolean allowsConsole() {
        return allowConsole;
    }

    public String getUsage(MCommand command) {
        return "/" + command.getName() + " " + originalSyntax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandSyntax)) return false;
        CommandSyntax that = (CommandSyntax) o;
        return allowConsole == that.allowConsole && Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params, allowConsole);
    }

}
