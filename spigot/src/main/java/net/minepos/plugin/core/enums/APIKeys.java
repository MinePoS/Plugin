package net.minepos.plugin.core.enums;

import lombok.Getter;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public enum APIKeys {
    CATEGORIES("get-categories", "get-category");

    @Getter private final String parameter;
    @Getter private final String individual;

    APIKeys(String parameter, String individual) {
        this.parameter = parameter;
        this.individual = individual;
    }
}
