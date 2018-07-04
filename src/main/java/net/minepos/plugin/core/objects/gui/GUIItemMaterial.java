package net.minepos.plugin.core.objects.gui;

import org.bukkit.Material;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class GUIItemMaterial {
    private enum GUIItemMaterialEnum {
        INTEGER(Integer.class),
        MATERIAL(Material.class),
        UNKNOWN(null);

        private final Class clazz;

        GUIItemMaterialEnum(Class clazz) {
            this.clazz = clazz;
        }

        public static GUIItemMaterialEnum fromClass(Class clazz) {
            for (GUIItemMaterialEnum type : values()) {
                if (type.clazz == clazz) {
                    return type;
                }
            }

            return UNKNOWN;
        }
    }

    private Integer amount;
    private Material material;

    public GUIItemMaterial(Object... others) {
        for (Object other : others) {
            switch (GUIItemMaterialEnum.fromClass(other.getClass())) {
                case INTEGER:
                    setAmount((Integer) other);
                    break;
                case MATERIAL:
                    setMaterial((Material) other);
                    break;
            }
        }
    }

    public GUIItemMaterial setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public GUIItemMaterial setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }
}
