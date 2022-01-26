package dev.mqzn.lib.npcs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.mqzn.lib.mLib;
import dev.mqzn.lib.utils.Translator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public abstract class NPCHuman extends NPC<EntityPlayer> {

	@Getter
	private final SkinData skinData;
	protected NPCHuman(Location location, String display, SkinData skinData) {
		super(location, display);
		this.skinData = skinData;
		this.entity = createEntity(location, display);
		this.id = entity.getId();
		initDW();
	}

	private void initDW() {
		DataWatcher watcher = entity.getDataWatcher();
		watcher.watch(10, (byte)127);
		this.setDataWatcher(watcher);
	}

	@Override
	public void show(Player player) {
		NPCHandler.INSTANCE.registerEntity(this);
		PacketPlayOutPlayerInfo tabPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entity);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(tabPacket);

		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(entity);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);


		new BukkitRunnable() {
			@Override
			public void run() {
				PacketPlayOutPlayerInfo infoRemoval = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entity);
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(infoRemoval);
			}
		}.runTaskLater(mLib.INSTANCE,50L);



		PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(entity, (byte) ((byte) entity.yaw * 256/360));
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(rotation);


	}


	@Override
	protected EntityPlayer createEntity(Location location, String display) {
		WorldServer server = ((CraftWorld)location.getWorld()).getHandle();

		GameProfile profile = new GameProfile(UUID.randomUUID(), Translator.color(display));
		profile.getProperties().put("textures", new Property("textures", skinData.getTexture(), skinData.getSignature()));

		EntityPlayer player = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), server, profile, new PlayerInteractManager(server));
		player.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		player.setCustomNameVisible(true);
		player.setCustomName(Translator.color(display));
		player.setInvisible(false);

		return player;
	}
}
