package net.minepos.plugin.common.file.implementations;

import net.minepos.plugin.common.file.framework.AbstractFileConfiguration;
import net.minepos.plugin.common.file.framework.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

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
    public Object get(String path) {
        return bukkitConfig.get(path);
    }

    @Override
    public FileConfiguration getConfigSection(String path) {
        return new SpigotFileConfiguration(bukkitConfig.getConfigurationSection(path));
    }

    @Override
    public String getString(String path) {
        return bukkitConfig.getString(path);
    }

    @Override
    public Integer getInt(String path) {
        return bukkitConfig.getInt(path);
    }

    @Override
    public Long getLong(String path) {
        return bukkitConfig.getLong(path);
    }

    @Override
    public Double getDouble(String path) {
        return bukkitConfig.getDouble(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return bukkitConfig.getBoolean(path);
    }

    @Override
    public final List<String> getStringList(String path) {
        return bukkitConfig.getStringList(path);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final List<FileConfiguration> getConfigList(String path) {
        List<?> list = getList(path);

        for (Object obj : list) {
            if (obj instanceof ConfigurationSection) {
                return ((List<ConfigurationSection>) list).stream().map(SpigotFileConfiguration::new).collect(Collectors.toList());
            }
        }

        return null;
    }

    @Override
    public final List<?> getList(String path) {
        return bukkitConfig.getList(path);
    }
}
