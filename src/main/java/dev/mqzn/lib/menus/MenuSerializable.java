package dev.mqzn.lib.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface MenuSerializable {


    /**
     * This method converts the object into an itemStack
     * to be serialized into the inventory to be viewed !
     * @return the itemStack of this object
     */
    ItemStack serialize(Player player);


    /**
     * @param e event when a player clicks on this icon
     */
    void onCLick(InventoryClickEvent e);

}
