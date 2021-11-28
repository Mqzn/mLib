package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.mLib;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class Menu implements MenuEntity {

    Inventory inventory = null;
    private final HashMap<Integer, StoredItem> storedItems = new HashMap<>();

    public Menu() {

    }

    protected abstract int getRows();

    protected abstract void setItems(Player player);


    protected void setItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> onClick) {
        inventory.setItem(slot, itemStack);
        storedItems.put(slot, StoredItem.of(slot, itemStack).withAction(onClick));
    }

    protected void addItem(ItemStack itemStack, Consumer<InventoryClickEvent> onClick) {
        int slot = inventory.firstEmpty();
        if(slot < 0) return;

        this.setItem(slot, itemStack, onClick);
    }

    protected StoredItem getItemAt(int slot) {
        return storedItems.get(slot);
    }

    protected void updateItem(int slot, BiFunction<ItemStack, Consumer<InventoryClickEvent>, StoredItem> update) {
        StoredItem item = this.getItemAt(slot);
        if(item == null) return;
        item = update.apply(item.getItem(), item.getActions());
        this.setItem(slot, item.getItem(), item.getActions());

        for(HumanEntity humanEntity : inventory.getViewers()) {
            if(!(humanEntity instanceof Player)) continue;
            Player player = (Player)humanEntity;

            MenuManager.getInstance().updateEntityFor(player, (menuEntity -> this));
            player.updateInventory();
        }

    }


    protected int calculateSize() {
        return this.getRows()*9;
    }


    @Override
    public void parseOnClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        StoredItem item = this.getItemAt(e.getSlot());
        if(item != null && item.hasActions()) {
            item.executeActions(e);
        }

        e.setCancelled(true);
    }


    @Override
    public void open(Player player) {

        inventory = Bukkit.createInventory(null,
                calculateSize(), Translator.color(this.getTitle() == null
                        ? this.getUniqueName() : this.getTitle()));

        this.setItems(player);
        if(MenuEntity.getOpenEntity(player) != null) {
            player.closeInventory();
            Bukkit.getScheduler().runTaskLater(mLib.INSTANCE, ()-> {
                MenuEntity.registerEntity(player, this);
                player.openInventory(inventory);
            }, 2L);
        }else {
            MenuEntity.registerEntity(player, this);
            player.openInventory(inventory);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;

        return this.getRows() == menu.getRows() &&
                this.getUniqueName().equalsIgnoreCase(menu.getUniqueName()) &&
                Objects.equal(inventory, menu.inventory) &&
                Objects.equal(storedItems, menu.storedItems);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUniqueName(),
                getRows(), inventory, storedItems);
    }

}
