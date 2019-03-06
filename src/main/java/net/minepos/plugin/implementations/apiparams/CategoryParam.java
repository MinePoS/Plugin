package net.minepos.plugin.implementations.apiparams;

import net.minepos.plugin.core.enums.APIKeys;
import net.minepos.plugin.core.objects.file.JFileConfiguration;
import net.minepos.plugin.core.objects.mineposapi.objects.Category;
import net.minepos.plugin.framework.APIParam;
import org.apache.commons.lang3.BooleanUtils;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CategoryParam extends APIParam {
    public CategoryParam() {
        super(APIKeys.CATEGORIES);
    }

    @Override
    protected Object execute(JFileConfiguration json) {
        return Category.builder()
                .id(json.getInt("id"))
                .name(json.getString("name"))
                .shortDesc(json.getString("short_desc"))
                .visible(BooleanUtils.toBoolean(json.getInt("visible")))
                .material(json.getString("material"))
                .build();
    }
}
