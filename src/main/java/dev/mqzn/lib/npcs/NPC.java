package dev.mqzn.lib.npcs;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@EqualsAndHashCode
public class NPC<E extends EntityLiving> {

	@Getter
	private final int id;
	@Getter
	protected final E entity;
	@Getter @Setter(value = AccessLevel.PROTECTED)
	private DataWatcher dataWatcher;

	@Getter(value = AccessLevel.PACKAGE)
	private final Consumer<Player> onClick;
	public NPC(E entity, Consumer<Player> onClick) {
		this.entity = entity;
		this.id = entity.getId();
		this.dataWatcher = entity.getDataWatcher();
		this.onClick = onClick;
	}

	public void show(Player player) {
		PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(entity);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(spawnPacket);

		PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entity.getId(), dataWatcher, true);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(metadata);
	}

	public final void remove() {
		entity.getBukkitEntity().remove();
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(entity.getId());
		for(Player player : Bukkit.getOnlinePlayers())
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(destroy);
	}


}
