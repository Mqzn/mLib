package dev.mqzn.lib.menus;

import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class MenuManager {

    private static MenuManager INSTANCE;

    public static MenuManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MenuManager();
        }
        return INSTANCE;
    }

    private final ConcurrentHashMap<UUID, MenuEntity> openMenus;

    private MenuManager() {
        openMenus = new ConcurrentHashMap<>();
    }

    void updateEntityFor(Player player, Function<MenuEntity, MenuEntity> update) {
        openMenus.computeIfPresent(player.getUniqueId(),
                (viewerId, entity)-> update.apply(entity));
    }

    Collection<MenuEntity> getOpenMenus() {
        return openMenus.values();
    }


    MenuEntity getOpenMenu(UUID viewerId) {
        return openMenus.get(viewerId);
    }


    void register(UUID viewer, MenuEntity menu) {
        openMenus.put(viewer, menu);
    }

    void unregister(UUID viewer) {
        openMenus.remove(viewer);
    }


}
