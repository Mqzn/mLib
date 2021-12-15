package dev.mqzn.lib.npcs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.mqzn.lib.utils.Translator;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NPCHandler {

    public static NPCHandler INSTANCE = new NPCHandler();

    private final ConcurrentHashMap<Integer, NPC> npcs = new ConcurrentHashMap<>();

    public NPC getNPC(int npcId) {
        return npcs.get(npcId);
    }

    public Collection<? extends NPC> getNPCs() {
        return npcs.values();
    }

    public boolean npcExists(int npcId) {
        return npcs.containsKey(npcId);
    }


    public void createNPC(Location location, String display, SkinData skinData) {

        WorldServer server = ((CraftWorld)location.getWorld()).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), Translator.color(display));
        profile.getProperties().put("textures", new Property("textures", skinData.getTexture(), skinData.getSignature()));
        EntityPlayer player = new EntityPlayer(((CraftServer)Bukkit.getServer()).getServer(), server, profile, new PlayerInteractManager(server));
        player.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        player.setCustomNameVisible(true);
        player.setCustomName(Translator.color(display));
        player.setInvisible(false);

        NPC npc = new NPC(player, skinData);
        this.npcs.put(npc.getId(), npc);

        for(Player p : Bukkit.getOnlinePlayers()) {
            this.makeThemSee(p, npc);
        }


    }


    public void makeThemSee(Player player, NPC npc) {
        PacketPlayOutPlayerInfo tabPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc.getEntity());
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(tabPacket);

        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(npc.getEntity());
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

        PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(npc.getEntity(), (byte) (npc.getEntity().yaw * 256/360));
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(rotation);
    }

}
