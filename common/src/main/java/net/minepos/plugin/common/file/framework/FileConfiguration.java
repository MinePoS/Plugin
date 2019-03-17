package net.minepos.plugin.common.file.framework;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public interface FileConfiguration {
    Object get(String path, Object... def);

    FileConfiguration getConfigSection(String path, FileConfiguration... def);

    String getString(String path, String... def);

    int getInt(String path, Integer... def);

    long getLong(String path, Long... def);

    double getDouble(String path, Double... def);

    boolean getBoolean(String path, Boolean... def);

    List<String> getStringList(String path, List<String>... def);

    List<FileConfiguration> getConfigList(String path, List<FileConfiguration>... def);

    List<?> getList(String path, List<Object>... def);
}
