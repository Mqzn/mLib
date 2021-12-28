package dev.mqzn.lib.npcs;

import dev.mqzn.lib.utils.Translator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@EqualsAndHashCode
public abstract class NPC<E extends EntityLiving> {

	@Getter
	private final int id;
	@Getter @Setter(value = AccessLevel.PROTECTED)
	private DataWatcher dataWatcher;
	protected final E entity;
	@Getter
	protected final Location loc;

	@Getter
	protected final String display;

	public NPC(Location location, String display) {
		this.display = Translator.color(display);
		this.loc = location;
		this.entity = createEntity(location, display);
		this.id = entity.getId();
	}

	public abstract void onClick(Player clicker);

	public void show(Player player) {
		PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(entity);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(spawnPacket);

		PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entity.getId(), dataWatcher, true);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(metadata);
	}

	public final void remove() {
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(id);
		for(Player player : Bukkit.getOnlinePlayers())
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(destroy);
	}

	protected abstract E createEntity(Location location, String display);

}
