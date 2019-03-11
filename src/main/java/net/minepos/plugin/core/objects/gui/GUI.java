package net.minepos.plugin.core.objects.gui;

import lombok.Builder;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Builder(toBuilder = true) @Data
public final class GUI {
    private String name;
    private int slots;
    private List<GUIItem> items;

    private Inventory inventory;

    public static class GUIBuilder {
        private GUIBuilder inventory(Inventory inventory) {
            return this;
        }

        public GUIBuilder items(GUIItem... items) {
            if (items != null) {
                this.items = Arrays.asList(items);
            }

            return this;
        }

        // "unused" method is actually used by lombok for the toBuilder method.
        public GUIBuilder items(List<GUIItem> items) {
            this.items = items;
            return this;
        }

        public GUI build() {
            inventory = Bukkit.createInventory(null, slots, name);

            for (GUIItem item : items) {
                inventory.setItem(item.getSlot(), item.getItemStack());
            }

            return new GUI(name, slots, items, inventory);
        }
    }
}
