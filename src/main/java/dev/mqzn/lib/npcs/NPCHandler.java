package dev.mqzn.lib.npcs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.mqzn.lib.utils.Translator;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

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


    public void createHumanNPC(Location location, String display, SkinData skinData, Consumer<Player> onClick) {

        WorldServer server = ((CraftWorld)location.getWorld()).getHandle();

        GameProfile profile = new GameProfile(UUID.randomUUID(), Translator.color(display));
        profile.getProperties().put("textures", new Property("textures", skinData.getTexture(), skinData.getSignature()));

        EntityPlayer player = new EntityPlayer(((CraftServer)Bukkit.getServer()).getServer(), server, profile, new PlayerInteractManager(server));
        player.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        player.setCustomNameVisible(true);
        player.setCustomName(Translator.color(display));
        player.setInvisible(false);

        NPCHuman npc = new NPCHuman(player, skinData, onClick);
        this.npcs.put(npc.getId(), npc);

        for(Player p : Bukkit.getOnlinePlayers()) {
            npc.show(p);
        }

    }



}
