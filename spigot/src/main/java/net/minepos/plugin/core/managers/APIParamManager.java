package net.minepos.plugin.core.managers;

import com.google.inject.Singleton;
import lombok.Getter;
import net.minepos.plugin.core.enums.APIKeys;
import net.minepos.plugin.core.objects.file.JFileConfiguration;
import net.minepos.plugin.framework.APIParam;

import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class APIParamManager {
    @Getter private final Map<APIKeys, APIParam> params = new HashMap<>();

    public Object run(APIKeys key, JFileConfiguration json) {
        return params.get(key).run(json);
    }

    public void put(APIParam param) {
        params.put(param.getKey(), param);
    }
}
