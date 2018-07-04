package net.minepos.plugin.core.objects.gui;

import net.minepos.plugin.core.objects.enums.gui.GUIBuilderConstructorEnum;
import net.minepos.plugin.core.utils.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class GUIBuilder {
    private Player player;
    private Integer size;
    private String title;
    private List<GUIItem> items;

    @SuppressWarnings("unchecked")
    public GUIBuilder(Player player, Object... others) {
        this.player = player;
        this.items = new ArrayList<>();

        for (Object other : others) {
            switch (GUIBuilderConstructorEnum.fromClass(other.getClass())) {
                case INTEGER:
                    setSize((Integer) other);
                    break;

                case STRING:
                    setTitle((String) other);
                    break;

                case ARRAYLIST:
                    if (other instanceof List<?>) {
                        for (Object object : (List<?>) other) {
                            if (object instanceof GUIItem) {
                                this.items = (List<GUIItem>) other;
                                break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    public GUIBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public GUIBuilder setTitle(String title) {
        this.title = StringUtils.cc("&r" + title);
        return this;
    }

    public GUIBuilder addItems(GUIItem... items) {
        this.items.addAll(Arrays.asList(items));
        return this;
    }

    public void build() {
        Inventory inventory = Bukkit.createInventory(null, size, title);

        for (GUIItem item : items) {
            inventory.setItem(item.getSlot(), item.getItemStack());
        }

        player.openInventory(inventory);
    }
}
