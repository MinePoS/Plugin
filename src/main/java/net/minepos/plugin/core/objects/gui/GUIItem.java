package net.minepos.plugin.core.objects.gui;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class GUIItem {
    private enum GUIItemEnum {
        INTEGER(Integer.class),
        ITEMSTACK(ItemStack.class),
        UNKNOWN(null);

        private final Class clazz;

        GUIItemEnum(Class clazz) {
            this.clazz = clazz;
        }

        public static GUIItemEnum fromClass(Class clazz) {
            for (GUIItemEnum type : values()) {
                if (type.clazz == clazz) {
                    return type;
                }
            }

            return UNKNOWN;
        }
    }

    @Getter private Integer slot;
    @Getter private ItemStack itemStack;

    public GUIItem(Object... others) {
        for (Object other : others) {
            switch (GUIItemEnum.fromClass(other.getClass())) {
                case INTEGER:
                    setSlot((Integer) other);
                    break;

                case ITEMSTACK:
                    setItemStack((ItemStack) other);
                    break;
            }
        }
    }

    public GUIItem setSlot(Integer slot) {
        this.slot = slot;
        return this;
    }

    public GUIItem setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }
}
