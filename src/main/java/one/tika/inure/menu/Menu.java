package one.tika.inure.menu;

import one.tika.inure.util.ItemUtil;
import one.tika.inure.util.Txt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {

    protected Inventory inventory;
    protected Player user;

    public Menu(Player user) {
        this.user = user;
    }

    public abstract String getMenuName();

    public abstract int getRows();

    public abstract void setMenuItems();

    public abstract void handleMenu(InventoryClickEvent event);

    public abstract void handleClose(InventoryCloseEvent event);

    public abstract boolean isStealable();

    public void open() {
        inventory = Bukkit.createInventory(this, getRows() * 9, Txt.color(getMenuName()));
        this.setMenuItems();
        user.openInventory(inventory);
    }

    public void setFillerGlass(Material glassMaterial){
        for (int i = 0; i < getRows() * 9; i++) {
            if (inventory.getItem(i) == null) inventory.setItem(i, ItemUtil.createItem(glassMaterial, " "));
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
