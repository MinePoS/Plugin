package net.minepos.plugin.core.objects.gui;


import net.minepos.plugin.core.utils.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class GUIItemBuilder {
    private enum ConstructorEnum {
        INTEGER(Integer.class),
        ITEMSTACK(ItemStack.class),
        ITEMMETA(ItemMeta.class),
        GUIITEMMATERIAL(GUIItemMaterial.class),
        STRING(String.class),
        ARRAYLIST(ArrayList.class),
        UUID(UUID.class),
        UNKNOWN(null);

        private final Class clazz;

        ConstructorEnum(Class clazz) {
            this.clazz = clazz;
        }

        public static ConstructorEnum fromClass(Class clazz) {
            for (ConstructorEnum type : values()) {
                if (type.clazz == clazz) {
                    return type;
                }
            }

            return UNKNOWN;
        }
    }

    private GUIItem guiItem;
    private Integer slot;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private GUIItemMaterial guiItemMaterial;
    private String displayName;
    private List<String> description;
    private UUID skullOwner;

    @SuppressWarnings("unchecked")
    public GUIItemBuilder(Object... others) {
        description = new ArrayList<>();

        for (Object other : others) {
            switch (ConstructorEnum.fromClass(other.getClass())) {
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
        this.description = StringUtils.cc(description);
        return this;
    }

    public GUIItemBuilder setSkullOwner(UUID skullOwner) {
        this.skullOwner = skullOwner;
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

                ItemStack itemStack;
                if (skullOwner == null) {
                    itemStack = new ItemStack(guiItemMaterial.getMaterial(), guiItemMaterial.getAmount());
                } else {
                    itemStack = new ItemStack(guiItemMaterial.getMaterial(), guiItemMaterial.getAmount(), (byte) SkullType.PLAYER.ordinal());
                }

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

                if (skullOwner != null && itemMeta instanceof SkullMeta) {
                    ((SkullMeta) itemMeta).setOwningPlayer(Bukkit.getOfflinePlayer(skullOwner));
                }

                itemStack.setItemMeta(itemMeta);
                this.guiItem = new GUIItem(slot, itemStack);
            }
        }

        return guiItem;
    }
}
