package net.minepos.plugin.core.objects.gui;

import lombok.Getter;
import net.minepos.plugin.core.utils.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class GUIBuilder {
    private enum ConstructorEnum {
        INTEGER(Integer.class),
        STRING(String.class),
        ARRAYLIST(ArrayList.class),
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

    private Integer size;
    private String title;
    private List<GUIItem> items;

    @SuppressWarnings("unchecked")
    public GUIBuilder(Object... others) {
        this.items = new ArrayList<>();

        for (Object other : others) {
            switch (ConstructorEnum.fromClass(other.getClass())) {
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

    public GUI build() {
        Inventory inventory = Bukkit.createInventory(null, size, title);

        for (GUIItem item : items) {
            inventory.setItem(item.getSlot(), item.getItemStack());
        }

        return new GUI(inventory, this);
    }

    public final class GUI {
        @Getter private final Inventory inventory;
        @Getter private final GUIBuilder builder;

        private GUI() throws InstantiationException {
            throw new InstantiationException("This class cannot be instantiated");
        }

        public GUI(Inventory inventory, GUIBuilder builder) {
            this.inventory = inventory;
            this.builder = builder;
        }
    }
}
