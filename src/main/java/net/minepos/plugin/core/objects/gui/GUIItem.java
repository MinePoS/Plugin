package net.minepos.plugin.core.objects.gui;

import org.bukkit.inventory.ItemStack;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class GUIItem {
    private Integer slot;
    private ItemStack itemStack;

    public GUIItem(Integer slot, ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public GUIItem setSlot(Integer slot) {
        this.slot = slot;
        return this;
    }

    public GUIItem setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public Integer getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
