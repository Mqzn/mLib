package dev.mqzn.lib.utils;

import com.google.common.base.Objects;
import org.bukkit.enchantments.Enchantment;

public final class ItemEnchant {

    private final Enchantment enchantment;
    private final int level;

    private ItemEnchant(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public static ItemEnchant of(Enchantment enchantment, int level) {
        return new ItemEnchant(enchantment, level);
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemEnchant)) return false;
        ItemEnchant that = (ItemEnchant) o;
        return getLevel() == that.getLevel() &&
                Objects.equal(getEnchantment(), that.getEnchantment());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEnchantment(), getLevel());
    }
}
