package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.Menu;
import dev.mqzn.lib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestMenu extends Menu {

    @Override
    protected int getRows() {
        return 4;
    }

    @Override
    protected void setItems(Player player) {
        ItemStack gl = new ItemBuilder(Material.EMERALD_BLOCK)
                .setDisplay("&a&lGREEN LANTERN POWER")
                .setLore("&2In Brightest Days and In Blackest Nights&f,",
                        "&2No Evil shall escape my sight&f,",
                        "&2Those who worship evil's might",
                        "&2Shall beware green lantern's LIGHT").build();

        this.setItem(11, gl, (e) -> {
            player.setItemInHand(gl);
            player.sendMessage("&7You became a great &agreen lantern &7!");
            player.closeInventory();
        });

    }

    @Override
    public String getUniqueName() {
        return "JustTesting";
    }

    @Override
    public String getTitle() {
        return "&9TestMenu";
    }


}
