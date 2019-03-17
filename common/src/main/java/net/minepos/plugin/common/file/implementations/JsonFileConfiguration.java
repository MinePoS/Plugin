package net.minepos.plugin.common.file.implementations;

import net.minepos.plugin.common.file.framework.AbstractFileConfiguration;
import net.minepos.plugin.common.file.framework.FileConfiguration;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JsonFileConfiguration extends AbstractFileConfiguration {
    @Override
    public Object get(String path, Object... def) {
        return null;
    }

    @Override
    public FileConfiguration getConfigSection(String path, FileConfiguration... def) {
        return null;
    }

    @Override
    public String getString(String path, String... def) {
        return null;
    }

    @Override
    public int getInt(String path, int... def) {
        return 0;
    }

    @Override
    public long getLong(String path, long... def) {
        return 0;
    }

    @Override
    public double getDouble(String path, double... def) {
        return 0;
    }

    @Override
    public boolean getBoolean(String path, boolean... def) {
        return false;
    }

    @Override
    public List<String> getStringList(String path, List<String>... def) {
        return null;
    }

    @Override
    public List<Object> getList(String path, List<Object>... def) {
        return null;
    }

    @Override
    public List<FileConfiguration> getConfigList(String path, List<FileConfiguration>... def) {
        return null;
    }
}
