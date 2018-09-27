package net.minepos.plugin.core.storage.yaml;

import com.google.inject.Inject;
import net.minepos.plugin.core.utils.string.StringUtils;

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
        return StringUtils.format(mFile.getFileConfiguration("lang").getStringList(item), placeholders);
    }
}
