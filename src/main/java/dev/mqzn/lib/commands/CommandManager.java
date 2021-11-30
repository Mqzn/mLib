package dev.mqzn.lib.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import java.lang.reflect.Field;

public class CommandManager {

    private static CommandManager INSTANCE;

    public static CommandManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommandManager();
        }
        return INSTANCE;
    }

    private CommandMap commandMap = null;


     {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private CommandManager() { }

    public <C extends Command> void  registerCommand(C command) {
        commandMap.register(command.getName(), command);
    }


}
