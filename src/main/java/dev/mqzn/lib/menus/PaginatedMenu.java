package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.mLib;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PaginatedMenu<S extends MenuSerializable> implements MenuEntity {

    private final int pageCapacity;
    private int currentPageIndex, maxPages;
    private final List<S> items = new ArrayList<>();
    private final ConcurrentHashMap<Integer, MenuPage<S>> pages = new ConcurrentHashMap<>();

    private final Set<Integer> boundSlots = new HashSet<>();
    {
        int next = getNextPageSlot();
        int previous = getPreviousPageSlot();
        int size = getPageRows()*9;

        if(next >= 0 && next < size) boundSlots.add(next);
        if(previous >=0 && previous < size) boundSlots.add(previous);

        pageCapacity = size*9-boundSlots.size();
    }

    protected PaginatedMenu() {
        currentPageIndex = 1;
    }

    protected PaginatedMenu(final int... boundSlots) {
        this();
        for(int s : boundSlots) this.boundSlots.add(s);
    }

    public int getPageCapacity() {
        return pageCapacity;
    }

    protected abstract void setItems(Player player);
    protected abstract int getPageRows();
    protected abstract int getNextPageSlot();
    protected abstract int getPreviousPageSlot();

    protected final void setPages() {
        this.maxPages = calculateMaxPages();
        pages.clear();

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
        return (int)Math.ceil((double)this.items.size()/pageCapacity);
    }

    protected final S getObject(int pageIndex, int slot) {

        MenuPage<S> page = this.getPageAt(pageIndex);
        if(page == null || slot < 0 || slot > page.calculateSize()-1) return null;

        int limit = Math.min(page.getMaxBound(), this.items.size());
        for (int index = page.getMinBound(); index < limit; index++) {
            int currentSlot = Math.abs(limit-index-pageCapacity);
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
        if(currentMenu == null || (!(currentMenu instanceof PaginatedMenu<?>) && !currentMenu.equals(this)) ) {
            //Doesn't have the paginated menus open (whatever page)
            System.out.println("ENTERED THIS");
            this.setItems(player);
            this.setPages();
        }


        if(pageIndex < 1 || pageIndex > maxPages)  {
            player.closeInventory();

            if(maxPages == 0) {
                player.sendMessage(Translator.color("&cThere are no pages to open !"));
                return;
            }

            player.sendMessage(Translator.color("&cInvalid Page Index: " + pageIndex));
            player.sendMessage(Translator.color("&cMaximum Available Pages: 1-" + maxPages));
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
