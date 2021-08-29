package one.tika.inure.menu;

import dev.dbassett.skullcreator.SkullCreator;
import one.tika.inure.util.ItemUtil;
import one.tika.inure.util.Txt;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class PaginatedMenu extends Menu {

    //Keep track of what page the menu is on
    protected int page = 0;

    //the index represents the index of the slot
    //that the loop is on
    protected int index = 0;

    public PaginatedMenu(Player user) {
        super(user);
    }

    public abstract PageTheme getTheme();

    public abstract List<ItemStack> getItems();

    public abstract void handleClick(InventoryClickEvent event);

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (!event.getClick().isLeftClick()) return;

        if (!event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            handleClick(event);
            return;
        }

        String clickedName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

        switch (clickedName.toLowerCase()) {
            case "left":
                if (page != 0) {
                    page = page - 1;
                    super.open();
                } else user.sendMessage(Txt.color("&cYou are on the first page"));
                return;
            case "right":
                if (index + 1 < getItems().size()) {
                    page = page + 1;
                    super.open();
                } else user.sendMessage(Txt.color("&cYou are on the last page!"));
                return;
            case "close":
                user.closeInventory();
        }
    }

    public abstract String getLeftArrow();
    public abstract String getRightArrow();
    public abstract String getCross();

    @Override
    public void setMenuItems() {
        for (int i : getTheme().getFillSlots(getRows()))
            inventory.setItem(i, ItemUtil.createItem(Material.GRAY_STAINED_GLASS_PANE, " "));

        List<Integer> cSlots = getTheme().getControlSlots(getRows());

        inventory.setItem(cSlots.get(0),
                ItemUtil.editItem(SkullCreator.itemFromBase64(getLeftArrow()), "&2Left"));

        inventory.setItem(cSlots.get(1),
                ItemUtil.editItem(SkullCreator.itemFromBase64(getCross()), "&cClose"));

        inventory.setItem(cSlots.get(2),
                ItemUtil.editItem(SkullCreator.itemFromBase64(getRightArrow()), "&2Right"));

        // Set the items in the menu
        if(getItems() != null && !getItems().isEmpty()) {
            int x = 0;
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) != null) continue;

                index = (inventory.getSize() * page) + x;
                x += 1;

                if (index >= getItems().size()) break;

                if (getItems().get(index) != null) {
                    inventory.setItem(i, getItems().get(index));
                }
            }
        }
    }

}
