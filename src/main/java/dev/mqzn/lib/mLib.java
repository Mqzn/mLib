package dev.mqzn.lib;

import dev.mqzn.lib.commands.CommandManager;
import dev.mqzn.lib.commands.test.TestCommand;
import dev.mqzn.lib.menus.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class mLib extends JavaPlugin {

    public static mLib INSTANCE;



    @Override
    public void onEnable() {
        INSTANCE = this;

        //mLib test command
        CommandManager.getInstance().registerCommand(new TestCommand());

        //mLib menu listener
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic

        reloadConfig();
    }

}
