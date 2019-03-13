package net.minepos.plugin.core.objects.gui;

import lombok.Builder;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Builder(toBuilder = true) @Data
public final class GUIItem {
    // Builder.Default doesn't actually work lol, but it makes my job a bit easier.
    @Builder.Default private int slot = 0;
    private Material material;
    @Builder.Default private int amount = 0;
    private String displayName;
    private List<String> description;

    private ItemStack itemStack;

    public static class GUIItemBuilder {
        private GUIItemBuilder itemStack(ItemStack itemStack) {
            return this;
        }

        public GUIItemBuilder description(String description) {
            this.description = Arrays.asList(description.split("\\n"));
            return this;
        }

        // "unused" method is actually used by lombok for the toBuilder method.
        public GUIItemBuilder description(List<String> description) {
            this.description = description;
            return this;
        }

        public GUIItem build() {
            ItemStack itemStack = new ItemStack(material == null ? Material.BEDROCK : material, amount == 0 ? 1 : amount);
            ItemMeta meta = itemStack.getItemMeta();

            if (displayName != null) {
                meta.setDisplayName(displayName);
            }

            if (description != null) {
                meta.setLore(description);
            }

            itemStack.setItemMeta(meta);

            return new GUIItem(slot, material, amount, displayName, description, itemStack);
        }
    }
}
