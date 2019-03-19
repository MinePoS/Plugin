package net.minepos.plugin.common.file.framework;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class AbstractFileConfiguration implements FileConfiguration {
    protected final Map<String, Object> itemMap = new ConcurrentHashMap<>();

    public final void load(File file, String fileContent) {
        itemMap.put("file-content", fileContent);
        itemMap.put("file", file);
        internalLoad(file, fileContent);
    }

    protected void internalLoad(File file, String fileContent) {}

    public File getFile() {
        return (File) itemMap.get("file");
    }

    @SafeVarargs
    protected final <T> T value(T value, T nullValue, T... def) {
        return value == null ? def.length >= 1 ? def[0] : nullValue : value;
    }
}
