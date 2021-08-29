package one.tika.inure;

import one.tika.inure.menu.Menu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public final class Inure {

    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(JavaPlugin plugin) {
        Inure.plugin = plugin;
    }

    public static void onGuiClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        //If the holder extends Menu (is a custom menu)
        if (holder instanceof Menu) {
            Menu menu = (Menu) holder; // certain that holder is now a Menu

            event.setCancelled(!menu.isStealable());

            if (event.getCurrentItem() == null) return; // prevent NPE
            menu.handleMenu(event);
        }
    }

    public static void onGuiClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Menu) {
            Menu menu = (Menu) holder;
            menu.handleClose(event);
        }
    }

}
