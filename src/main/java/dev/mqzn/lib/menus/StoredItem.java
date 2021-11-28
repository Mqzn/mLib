package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public final class StoredItem {

    private final int slot;
    private final ItemStack item;
    private Consumer<InventoryClickEvent> actions = null;

    private StoredItem(int slot, ItemStack item, Consumer<InventoryClickEvent> actions) {
        this.slot = slot;
        this.item = item;
        this.actions = actions;
    }

    private StoredItem(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }


    public static StoredItem of(int slot, ItemStack item) {
        return new StoredItem(slot, item);
    }

    public static StoredItem of(int slot, ItemStack item, Consumer<InventoryClickEvent> actions) {
        return new StoredItem(slot, item, actions);
    }

    Consumer<InventoryClickEvent> getActions() {
        return actions;
    }

    public StoredItem withAction(Consumer<InventoryClickEvent> action) {

        if(this.hasActions()) {
            this.actions = actions.andThen(action);
        }else {
            this.setOneAction(action);
        }

        return this;
    }

    void setOneAction(Consumer<InventoryClickEvent> action) {
        this.actions = action;
    }

    public void executeActions(InventoryClickEvent event) {
        this.actions.accept(event);
    }


    boolean hasActions() {
        return actions != null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoredItem)) return false;
        StoredItem that = (StoredItem) o;

        return slot == that.slot && item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(slot, item);
    }

}
