package net.minepos.plugin.common.minepos.params.framework;

import lombok.Getter;
import net.minepos.plugin.common.file.implementations.JsonFileConfiguration;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class APIParam {
    @Getter private final APIParams key;

    protected APIParam(APIParams key) {
        this.key = key;
    }

    protected abstract Object execute(JsonFileConfiguration json);

    public Object run(JsonFileConfiguration json) {
        return execute(json);
    }
}
