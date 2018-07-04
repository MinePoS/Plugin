package net.minepos.plugin.core.objects.enums.gui;

import java.util.ArrayList;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public enum GUIBuilderConstructorEnum {
    INTEGER(Integer.class),
    STRING(String.class),
    ARRAYLIST(ArrayList.class),
    UNKNOWN(null);

    private final Class clazz;

    GUIBuilderConstructorEnum(Class clazz) {
        this.clazz = clazz;
    }

    public static GUIBuilderConstructorEnum fromClass(Class clazz) {
        for (GUIBuilderConstructorEnum type : values()) {
            if (type.clazz == clazz) {
                return type;
            }
        }

        return UNKNOWN;
    }
}
