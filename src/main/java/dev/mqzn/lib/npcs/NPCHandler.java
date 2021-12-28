package dev.mqzn.lib.npcs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class NPCHandler {

    public static NPCHandler INSTANCE = new NPCHandler();
    private final ConcurrentHashMap<Integer, NPC<?>> npcs = new ConcurrentHashMap<>();

    public NPC<?> getNPC(int npcId) {
        return npcs.get(npcId);
    }

    public Collection<? extends NPC<?>> getNPCs() {
        return npcs.values();
    }

    public boolean npcExists(int npcId) {
        return npcs.containsKey(npcId);
    }

    public void registerEntity(NPC<?> npc) {
        npcs.put(npc.getId(), npc);
    }


    public void spawnNPC(NPC<?> npc) {
        this.registerEntity(npc);
        for(Player p : Bukkit.getOnlinePlayers()) {
            npc.show(p);
        }
    }

    public void deSpawnNPC(NPC<?> npc) {
        npc.remove();
        npcs.remove(npc.getId());
    }

}
