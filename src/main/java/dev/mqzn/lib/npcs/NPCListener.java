package dev.mqzn.lib.npcs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NPCListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e) {

        for(NPC npc : NPCHandler.INSTANCE.getNPCs()) {
            NPCHandler.INSTANCE.makeThemSee(e.getPlayer(), npc);
        }

    }


}
