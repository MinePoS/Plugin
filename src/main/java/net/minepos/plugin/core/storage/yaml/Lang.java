package net.minepos.plugin.core.storage.yaml;

import com.google.inject.Inject;
import net.minepos.plugin.core.utils.string.StringUtils;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Lang {
    @Inject private MFile mFile;

    public String get(String item, Object... placeholders) {
        return StringUtils.cc(String.format(mFile.getFileConfiguration("lang").getString(item), placeholders));
    }

    public List<String> getList(String item, Object... placeholders) {
        List<String> list = new ArrayList<>();
        mFile.getFileConfiguration("lang").getStringList(item).stream().map(StringUtils::cc).forEach(str -> list.add(String.format(str, placeholders)));

        return list;
    }
}
