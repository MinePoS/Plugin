package net.minepos.plugin.framework;

import lombok.Getter;
import net.minepos.plugin.core.enums.APIKeys;
import net.minepos.plugin.core.objects.file.JFileConfiguration;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class APIParam {
    @Getter private final APIKeys key;

    protected APIParam(APIKeys key) {
        this.key = key;
    }

    protected abstract Object execute(JFileConfiguration json);

    public Object run(JFileConfiguration json) {
        return execute(json);
    }
}
