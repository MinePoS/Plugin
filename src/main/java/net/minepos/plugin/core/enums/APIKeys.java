package net.minepos.plugin.core.enums;

import lombok.Getter;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public enum APIKeys {
    CATEGORIES("get-categories");

    @Getter private final String parameter;

    APIKeys(String parameter) {
        this.parameter = parameter;
    }
}
