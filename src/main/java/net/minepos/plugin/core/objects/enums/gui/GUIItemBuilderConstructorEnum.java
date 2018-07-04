package net.minepos.plugin.core.objects.enums.gui;

import net.minepos.plugin.core.objects.gui.GUIItemMaterial;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public enum GUIItemBuilderConstructorEnum {
    INTEGER(Integer.class),
    ITEMSTACK(ItemStack.class),
    ITEMMETA(ItemMeta.class),
    GUIITEMMATERIAL(GUIItemMaterial.class),
    STRING(String.class),
    ARRAYLIST(ArrayList.class),
    UNKNOWN(null);

    private final Class clazz;

    GUIItemBuilderConstructorEnum(Class clazz) {
        this.clazz = clazz;
    }

    public static GUIItemBuilderConstructorEnum fromClass(Class clazz) {
        for (GUIItemBuilderConstructorEnum type : values()) {
            if (type.clazz == clazz) {
                return type;
            }
        }

        return UNKNOWN;
    }
}
