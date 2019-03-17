package net.minepos.plugin.common.file.implementations;

import net.minepos.plugin.common.file.framework.AbstractFileConfiguration;
import net.minepos.plugin.common.file.framework.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SpigotFileConfiguration extends AbstractFileConfiguration {
    private ConfigurationSection bukkitConfig;

    public SpigotFileConfiguration() {}

    private SpigotFileConfiguration(ConfigurationSection bukkitConfig) {
        this.bukkitConfig = bukkitConfig;
    }

    @Override
    protected void internalLoad(File file, String fileContent) {
        try {
            YamlConfiguration config = new YamlConfiguration();
            config.loadFromString(fileContent);
            bukkitConfig = config;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(String path, Object... def) {
        return bukkitConfig.get(path, def.length >= 1 ? def : null);
    }

    @Override
    public FileConfiguration getConfigSection(String path, FileConfiguration... def) {
        return value(new SpigotFileConfiguration(bukkitConfig.getConfigurationSection(path)), new SpigotFileConfiguration(), def);
    }

    @Override
    public String getString(String path, String... def) {
        return value(bukkitConfig.getString(path), "", def);
    }

    @Override
    public int getInt(String path, Integer... def) {
        return value(bukkitConfig.getInt(path), 0, def);
    }

    @Override
    public long getLong(String path, Long... def) {
        return value(bukkitConfig.getLong(path), 0L, def);
    }

    @Override
    public double getDouble(String path, Double... def) {
        return value(bukkitConfig.getDouble(path), 0D, def);
    }

    @Override
    public boolean getBoolean(String path, Boolean... def) {
        return value(bukkitConfig.getBoolean(path), false, def);
    }

    @SafeVarargs
    @Override
    public final List<String> getStringList(String path, List<String>... def) {
        return value(bukkitConfig.getStringList(path), new ArrayList<>(), def);
    }

    @SafeVarargs
    @Override
    public final List<FileConfiguration> getConfigList(String path, List<FileConfiguration>... def) {
        List<?> list = getList(path);
    }

    @SafeVarargs
    @Override
    public final List<?> getList(String path, List<Object>... def) {
        return value(bukkitConfig.getList(path), new ArrayList<>(), def);
    }
}
