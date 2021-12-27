package dev.mqzn.lib;

import dev.mqzn.lib.commands.CommandManager;
import dev.mqzn.lib.commands.test.TestCommand;
import dev.mqzn.lib.menus.MenuListener;
import dev.mqzn.lib.npcs.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class mLib extends JavaPlugin implements Listener {

    public static mLib INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        //mLib menu listener
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for(NPC<?> npc : NPCHandler.INSTANCE.getNPCs()) npc.remove();
        reloadConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new PacketInterceptor(e.getPlayer()).inject();
        for(NPC<?> npc : NPCHandler.INSTANCE.getNPCs()) {
            npc.show(e.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PacketInterceptor.unInject(e.getPlayer());
    }

    /*@EventHandler
    public void onClickNPC(NPCPlayerClickEvent e) {
        if(e.getClickedNPC() instanceof NPCHuman) {
            e.setCancelled(true);
            System.out.println("THIS IS JUST A TEST !!, NPC-CLICK-EVENT WAS CANCELLED");
        }
    }*/

}
