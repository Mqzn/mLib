package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.PaginatedMenu;
import org.bukkit.entity.Player;

public class TagsMenu extends PaginatedMenu<Tag> {

    public TagsMenu() {
        super();
    }

    @Override
    protected void setItems(Player player) {

        for (int i = 0; i < (10000) ; i++) {
            this.addItem(new Tag("&9Tag#" + i, "&aTAG(" + i + ")"));
        }

    }

    @Override
    protected int getPageRows() {
        return 0;
    }

    @Override
    protected int getNextPageSlot() {
        return 0;
    }

    @Override
    protected int getPreviousPageSlot() {
        return 0;
    }

    @Override
    public String getUniqueName() {
        return "tags";
    }

    @Override
    public String getTitle() {
        return "&d&lTags Menu";
    }


}
