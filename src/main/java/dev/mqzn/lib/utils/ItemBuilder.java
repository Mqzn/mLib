package dev.mqzn.lib.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    protected final ItemStack itemStack;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, int  amount, short damage) {
        itemStack = new ItemStack(material, amount, damage);
    }

    public ItemBuilder(ItemStack item) {
        itemStack = new ItemStack(item);
    }


    public ItemBuilder setDisplay(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Translator.color(name));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Translator.colorList(Arrays.asList(lore)));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Translator.colorList(lore));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(flags);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.removeItemFlags(flags);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder glow(boolean glow) {
        ItemMeta meta = itemStack.getItemMeta();

        if(glow) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }else {
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(meta);
        return this;
    }



    public ItemBuilder addEnchants(ItemEnchant... enchants) {
        ItemMeta meta = itemStack.getItemMeta();
        for (ItemEnchant itemEnchant : enchants) {
            meta.addEnchant(itemEnchant.getEnchantment(), itemEnchant.getLevel(), true);
        }
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder removeEnchants(Enchantment... enchantments) {
        ItemMeta meta = itemStack.getItemMeta();
        for(Enchantment e : enchantments) {
            meta.removeEnchant(e);
        }
        itemStack.setItemMeta(meta);
        return this;
    }



    public ItemStack build() {
        return itemStack;
    }


}

