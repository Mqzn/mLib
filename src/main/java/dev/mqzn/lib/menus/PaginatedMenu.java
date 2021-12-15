package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.mLib;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PaginatedMenu<S extends MenuSerializable> implements MenuEntity {

    private int currentPageIndex;
    private int maxPages;
    private final List<S> items = new ArrayList<>();
    private final ConcurrentHashMap<Integer, MenuPage<S>> pages = new ConcurrentHashMap<>();

    private final static int CAPACITY = (MenuPage.PAGE_ROWS*9)-2; //TODO make page rows configurable

    protected PaginatedMenu() {
        currentPageIndex = 1;
    }

    protected abstract void setItems(Player player);

    protected final void setPages() {
        pages.clear();
        this.maxPages = calculateMaxPages();

        int pageIndex = 1;
        while (pageIndex <= maxPages) {
            MenuPage<S> page = new MenuPage<>(pageIndex, this);
            this.setPage(pageIndex,  page);
            pageIndex++;
        }
    }

    protected void addItem(S serializable) {
        this.items.add(serializable);
    }


    final List<S> getItems() {
        return items;
    }

    protected final void setPage(int pageIndex, MenuPage<S> menu) {
        this.pages.put(pageIndex, menu);
    }

    protected final MenuPage<S> getPageAt(int pageIndex) {
        return this.pages.get(pageIndex);
    }

    private int calculateMaxPages() {
        return (int)Math.ceil((double)this.items.size()/CAPACITY);
    }

    protected final S getSerializableObject(int pageIndex, int slot) {

        MenuPage<S> page = this.getPageAt(pageIndex);
        if(page == null || slot < 0 || slot > page.calculateSize()-1) return null;

        int limit = Math.min(page.getMaxBound(), this.items.size());
        for (int index = page.getMinBound(); index < limit; index++) {
            int currentSlot = Math.abs(limit-index-MenuPage.PAGE_CAPACITY);
            if(currentSlot == slot) {
                return items.get(index) ;
            }
        }
        return null;
    }

    @Override
    public final void open(Player player) {
        this.openPage(player, 1);
    }

    public final void openPage(Player player, int pageIndex) {

        MenuEntity currentMenu = MenuEntity.getOpenEntity(player);
        if( !(currentMenu instanceof PaginatedMenu<?>) && !currentMenu.equals(this)) {
            //Doesn't have the paginated menus open (whatever page)

            this.setItems(player);
            this.setPages();
        }


        if(pageIndex < 1 || pageIndex > maxPages)  {
            player.closeInventory();
            player.sendMessage(Translator.color("&cInvalid Page Index: " + pageIndex));
            player.sendMessage(Translator.color("&cMaximum Available Pages: 1-" + (Math.max(1, maxPages))));
            return;
        }

        currentPageIndex = pageIndex;
        MenuPage<S> page = this.getPageAt(pageIndex);
        player.closeInventory();

        Bukkit.getScheduler().runTaskLater(mLib.INSTANCE, ()-> {
            MenuManager.getInstance().register(player.getUniqueId(), this);
            page.open(player);
        }, 2L);

    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    @Override
    public void parseOnClick(InventoryClickEvent e) {
        MenuPage<S> page = this.getPageAt(currentPageIndex);
        if(page != null) page.parseOnClick(e);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaginatedMenu)) return false;
        PaginatedMenu<?> that = (PaginatedMenu<?>) o;

        return this.getUniqueName().equals(that.getUniqueName()) &&
                maxPages == that.maxPages &&
                Objects.equal(items, that.items) &&
                Objects.equal(pages, that.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getUniqueName(),
                maxPages, items, pages);
    }


}
