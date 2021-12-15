package dev.mqzn.lib.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface MenuEntity{


    /**
     * A name for each menu to distinguish between each menu
     * @return the UNIQUE name of the menu entity
     */
    String getUniqueName();



    /**
     * Each menu entity must have a title
     * However, the way the title looks differs
     * within each menus entities
     *
     * @apiNote the title is coloured automatically
     * @return the title of the menu entity
     */
    String getTitle();



    /**
     * Opens the menu entity for the player
     * @param player the lucky guy to open the amazing menu for
     */
    void open(Player player);



    /**
     * Applies actions when clicking on a button !
     * @param e the click event triggered when clicking on a button
     */
    void parseOnClick(InventoryClickEvent e);



    /**
     * @param player the viewer of the open menu
     * A player cannot open more than 1 menu
     *
     * @apiNote It returns null if the player has no open menus entity !
     *
     * @return gets the current open menu for that player
     */
    static MenuEntity getOpenEntity(Player player) {
        return MenuManager.getInstance().getOpenMenu(player.getUniqueId());
    }

    /**
     *
     * @param player the player to store current menu entity opened
     * @param entity the menu entity to be stored with the player
     *
     * @see Menu
     * @see PaginatedMenu
     *
     * This basically caches the current open menu
     */

    static void registerEntity(Player player, MenuEntity entity) {
        if(player != null && entity != null)
        MenuManager.getInstance().register(player.getUniqueId(), entity);
    }


    static void unregisterEntity(Player player) {
        if(player != null)
        MenuManager.getInstance().unregister(player.getUniqueId());
    }


}
