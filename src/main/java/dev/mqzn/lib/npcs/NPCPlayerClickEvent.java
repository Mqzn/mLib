package dev.mqzn.lib.npcs;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NPCPlayerClickEvent extends Event implements Cancellable {
	private final static HandlerList handlers = new HandlerList();

	private boolean cancel = false;
	private final NPC<?> clickedNPC;
	private final Player clicker;

	public NPCPlayerClickEvent(Player clicker, NPC<?> clickedNPC) {
		this.clicker = clicker;
		this.clickedNPC = clickedNPC;
	}

	public Player getClicker() {
		return clicker;
	}

	public NPC<?> getClickedNPC() {
		return clickedNPC;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}


	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean b) {
		this.cancel = b;
	}

}
