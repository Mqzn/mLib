package dev.mqzn.lib.npcs;

import dev.mqzn.lib.mLib;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

@EqualsAndHashCode(callSuper = true)
public class NPCHuman extends NPC<EntityPlayer> {

	@Getter
	private final SkinData data;
	NPCHuman(EntityPlayer entity, SkinData data, Consumer<Player> onClick) {
		super(entity, onClick);
		initDW();
		this.data = data;
	}

	private void initDW() {
		DataWatcher watcher = entity.getDataWatcher();
		watcher.watch(10, (byte)127);
		this.setDataWatcher(watcher);
	}

	@Override
	public void show(Player player) {

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


		/*
		PacketPlayOutEntityHeadRotation rotation = new PacketPlayOutEntityHeadRotation(entity, (byte) ((byte) entity.yaw * 256/360));
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(rotation);
		*/

	}
}
