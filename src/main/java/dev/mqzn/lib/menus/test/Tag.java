package dev.mqzn.lib.menus.test;

import com.google.common.base.Objects;
import dev.mqzn.lib.menus.MenuSerializable;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.ItemEnchant;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Tag implements MenuSerializable {

    private final String name;
    private final String display;

    public Tag(String name, String display) {
        this.name = name;
        this.display = display;
    }


    public String getName() {
        return name;
    }

    public String getDisplay() {
        return display;
    }

    public String format(Player player) {
        return Translator.color(display + " &7" + player.getName());
    }


    @Override
    public ItemStack serialize(Player player) {
        return new ItemBuilder(Material.NAME_TAG)
                .setDisplay(Translator.formatCase(name))
                .setLore("&n&fDisplay >>",
                        display,
                        "",
                        "&f&nFormat >>",
                        this.format(player))
                .addEnchants(ItemEnchant.of(Enchantment.DURABILITY, 1))
                .addFlags(ItemFlag.values()).build();
    }

    @Override
    public void onCLick(InventoryClickEvent e) {

        Player player = (Player)e.getWhoClicked();
        player.sendMessage(Translator.color("&7You have selected the tag &a'" + name + "'"));
        player.closeInventory();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equal(getName(), tag.getName()) &&
                Objects.equal(getDisplay(), tag.getDisplay());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getDisplay());
    }



}


