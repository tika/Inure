package one.tika.inure;

import one.tika.inure.menu.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;

public final class Inure extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().log(Level.INFO, "Inure powering " + getInurePlugins() + " plugins.");
    }

    private int getInurePlugins() {
        return (int) Arrays.stream(getServer()
                .getPluginManager()
                .getPlugins())
                .filter(pl -> pl.getDescription().getDepend().contains(getDescription().getName()))
                .count();
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        //If the holder extends Menu (is a custom menu)
        if (holder instanceof Menu) {
            Menu menu = (Menu) holder; // certain that holder is now a Menu

            event.setCancelled(!menu.isStealable());

            if (event.getCurrentItem() == null) return; // prevent NPE
            menu.handleMenu(event);
        }
    }

    @EventHandler
    public void onGuiClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Menu) {
            Menu menu = (Menu) holder;
            menu.handleClose(event);
        }
    }

}
