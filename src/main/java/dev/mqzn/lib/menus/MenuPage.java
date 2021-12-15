package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class MenuPage<S extends MenuSerializable> extends Menu {

    private final int index, min, max;
    private final PaginatedMenu<S> paginatedMenu;
    final static int PAGE_ROWS, PAGE_CAPACITY, NEXT_PAGE_SLOT, PREVIOUS_PAGE_SLOT;
    private final static ItemStack NEXT_PAGE_BUTTON, PREVIOUS_PAGE_BUTTON;

    static {
        PAGE_ROWS = 6;

        PAGE_CAPACITY = PAGE_ROWS*9-2;

        NEXT_PAGE_SLOT = (PAGE_ROWS*9)-1;

        NEXT_PAGE_BUTTON = new ItemBuilder(Material.ARROW)
                .setDisplay("&aNext Page >>").build();

        PREVIOUS_PAGE_SLOT = NEXT_PAGE_SLOT-8;

        PREVIOUS_PAGE_BUTTON = new ItemBuilder(Material.ARROW)
                .setDisplay("&e<< Previous Page").build();
    }

    public MenuPage(int index, PaginatedMenu<S> paginatedMenu) {
        this.paginatedMenu = paginatedMenu;
        this.index = index;

         // as there will be extra buttons
        this.max = index*PAGE_CAPACITY;
        this.min = max-PAGE_CAPACITY;

    }

    public int getIndex() {
        return index;
    }

    int getMaxBound() {
        return max;
    }

    int getMinBound() {
        return min;
    }


    @Override
    protected int getRows() {
        return PAGE_ROWS;
    }


    @Override
    protected void setItems(Player player) {

        this.setItem(NEXT_PAGE_SLOT, NEXT_PAGE_BUTTON,
                (e) -> {
                    paginatedMenu.openPage(player, index+1);
                    System.out.println("OPENED PAGE " + (index+1));
                });

        this.setItem(PREVIOUS_PAGE_SLOT, PREVIOUS_PAGE_BUTTON,
                (e)-> {
                    paginatedMenu.openPage(player, index-1);
                    System.out.println("OPENED PAGE " + (index-1));
                });

        int limit = Math.min(max, paginatedMenu.getItems().size());
        for (int slot = min; slot < limit; slot++) {
            MenuSerializable icon = paginatedMenu.getItems().get(slot);
            this.addItem(icon.serialize(player), icon::onCLick);
        }

    }

    @Override
    public String getUniqueName() {
        return "Page #" + index + " In " + this.paginatedMenu.getUniqueName();
    }

    @Override
    public String getTitle() {
        return Translator.color(this.paginatedMenu.getTitle() + " &7#" + index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuPage)) return false;
        if (!super.equals(o)) return false;
        MenuPage<?> page = (MenuPage<?>) o;
        return getIndex() == page.getIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getIndex());
    }

    @Override
    public String toString() {
        return "MenuPage{" +
                "index=" + index +
                ", min=" + min +
                ", max=" + max +
                ", paginatedMenu=" + paginatedMenu.getUniqueName() +
                '}';
    }


}
