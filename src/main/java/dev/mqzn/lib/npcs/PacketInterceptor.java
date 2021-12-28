package dev.mqzn.lib.npcs;

import dev.mqzn.lib.utils.Reflection;
import io.netty.channel.*;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PacketInterceptor extends ChannelDuplexHandler {

	@NotNull
	private static final Reflection.FieldAccessor<Integer> ENTITY_ID_FIELD = Reflection.getField(PacketPlayInUseEntity.class, int.class, 0);

	@NotNull
	private final Player player;

	public PacketInterceptor(@NotNull Player player) {
		super();
		this.player = player;
	}

	@Override
	public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
		this.handlePackets(context, packet, null, false);
	}

	@Override
	public void write(ChannelHandlerContext context, Object packet, ChannelPromise channelPromise) throws Exception {
		this.handlePackets(context, packet, channelPromise, true);
	}


	private void handlePackets(ChannelHandlerContext context, Object packet, ChannelPromise channelPromise, boolean write) throws Exception {

		if(packet instanceof PacketPlayInUseEntity) {
			PacketPlayInUseEntity useEntity = (PacketPlayInUseEntity) packet;
			int id = ENTITY_ID_FIELD.get(useEntity);
			NPC<?> entity = NPCHandler.INSTANCE.getNPC(id);
			//blocking using that entity
			if(entity == null) {
				if(write) {
					super.write(context, packet, channelPromise);
				}else {
					super.channelRead(context, packet);
				}
				return;
			}

			NPCPlayerClickEvent event = new NPCPlayerClickEvent(player, entity);
			Bukkit.getPluginManager().callEvent(event);

			if(!event.isCancelled()) {
				entity.onClick(player);
			}
		}

		if(write) {
			super.write(context, packet, channelPromise);
		}else {
			super.channelRead(context, packet);
		}
	}


	public void inject() {
		ChannelPipeline pipeline = ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel.pipeline();
		pipeline.addBefore("packet_handler", player.getName(), this);
	}

	public static void unInject(Player player) {
		Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(player.getName());
		});
	}

	public static void injectPlayers() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			new PacketInterceptor(player).inject();
		}
	}


}
