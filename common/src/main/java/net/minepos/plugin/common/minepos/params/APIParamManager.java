package net.minepos.plugin.common.minepos.params;

import com.google.inject.Singleton;
import lombok.Getter;
import net.minepos.plugin.common.file.implementations.JsonFileConfiguration;
import net.minepos.plugin.common.minepos.params.framework.APIParam;
import net.minepos.plugin.common.minepos.params.framework.APIParams;

import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class APIParamManager {
    @Getter private final Map<APIParams, APIParam> params = new HashMap<>();

    public Object run(APIParams param, JsonFileConfiguration json) {
        return params.get(param).run(json);
    }

    public void put(APIParam param) {
        params.put(param.getKey(), param);
    }
}
