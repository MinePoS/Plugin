package net.minepos.plugin.core.objects.gui;


import net.minepos.plugin.core.objects.enums.gui.GUIItemBuilderConstructorEnum;
import net.minepos.plugin.core.utils.string.StringUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class GUIItemBuilder {
    private GUIItem guiItem;
    private Integer slot;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private GUIItemMaterial guiItemMaterial;
    private String displayName;
    private List<String> description;

    @SuppressWarnings("unchecked")
    public GUIItemBuilder(Object... others) {
        description = new ArrayList<>();

        for (Object other : others) {
            switch (GUIItemBuilderConstructorEnum.fromClass(other.getClass())) {
                case INTEGER:
                    setSlot((Integer) other);
                    break;

                case ITEMSTACK:
                    setItemStack((ItemStack) other);
                    break;

                case ITEMMETA:
                    setItemMeta((ItemMeta) other);
                    break;

                case GUIITEMMATERIAL:
                    setGuiItemMaterial((GUIItemMaterial) other);
                    break;

                case STRING:
                    setDisplayName((String) other);
                    break;

                case ARRAYLIST:
                    if (other instanceof List<?>) {
                        for (Object object : (List<?>) other) {
                            if (object instanceof String) {
                                setDescription((List<String>) other);
                                break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    public GUIItemBuilder setSlot(Integer slot) {
        this.slot = slot;
        return this;
    }

    public GUIItemBuilder setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public GUIItemBuilder setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    public GUIItemBuilder setGuiItemMaterial(GUIItemMaterial guiItemMaterial) {
        this.guiItemMaterial = guiItemMaterial;
        return this;
    }

    public GUIItemBuilder setDisplayName(String displayName) {
        this.displayName = StringUtils.cc("&r" + displayName);
        return this;
    }

    public GUIItemBuilder setDescription(List<String> description) {
        List<String> newDesc = new ArrayList<>();
        description.forEach(str -> newDesc.add(StringUtils.cc("&r" + str)));

        this.description = newDesc;
        return this;
    }

    public GUIItem build() {
        if (slot != null) {
            if (itemStack != null) {
                if (itemMeta != null) {
                    itemStack.setItemMeta(itemMeta);
                }

                guiItem = new GUIItem(slot, itemStack);
            } else {
                if (guiItemMaterial == null) return null;

                ItemStack itemStack = new ItemStack(guiItemMaterial.getMaterial(), guiItemMaterial.getAmount());
                ItemMeta itemMeta = itemStack.getItemMeta();

                if (this.itemMeta != null) {
                    itemStack.setItemMeta(this.itemMeta);
                    guiItem = new GUIItem(slot, itemStack);
                    return null;
                }

                if (displayName != null) {
                    itemMeta.setDisplayName(displayName);
                }

                if (description != null) {
                    itemMeta.setLore(description);
                }

                itemStack.setItemMeta(itemMeta);
                this.guiItem = new GUIItem(slot, itemStack);
            }
        }

        return guiItem;
    }
}
