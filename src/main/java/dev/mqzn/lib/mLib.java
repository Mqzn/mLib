package dev.mqzn.lib;

import dev.mqzn.lib.menus.MenuListener;
import dev.mqzn.lib.npcs.NPC;
import dev.mqzn.lib.npcs.NPCHandler;
import dev.mqzn.lib.npcs.PacketInterceptor;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
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

        //CommandManager.getInstance().registerCommand(new TestCommand());

        //mLib menu listener
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for(NPC<?> npc : NPCHandler.INSTANCE.getNPCs()) NPCHandler.INSTANCE.deSpawnNPC(npc);
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


    public static void sendPacket(Player p, Packet<?> packet) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
}
