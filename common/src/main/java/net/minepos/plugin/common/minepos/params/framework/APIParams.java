package net.minepos.plugin.common.minepos.params.framework;

import lombok.Getter;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public enum APIParams {
    CATEGORIES("get-categories", "get-category");

    @Getter private final String parameter;
    @Getter private final String individual;

    APIParams(String parameter, String individual) {
        this.parameter = parameter;
        this.individual = individual;
    }
}
