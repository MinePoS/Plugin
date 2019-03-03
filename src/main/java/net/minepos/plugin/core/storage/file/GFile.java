package net.minepos.plugin.core.storage.file;

import com.google.inject.Singleton;
import net.minepos.plugin.MineposPlugin;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class GFile {
    private final Map<String, Map<String, Object>> itemMaps = new ConcurrentHashMap<>();
    private final Logger logger = Bukkit.getLogger();

    public void make(String name, String externalPath, String internalPath) {
        File file = new File(externalPath);

        try {
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.getParentFile().mkdirs();

                if (file.createNewFile()) {
                    if (net.minepos.plugin.core.utils.file.FileUtils.exportResource(MineposPlugin.class.getResourceAsStream(internalPath), externalPath)) {
                        insertIntoMap(name, file);

                        logger.info(name + " successfully created & loaded.");
                    } else {
                        logger.severe(name + " creation failed.");
                    }
                } else {
                    logger.severe(name + " creation failed.");
                }
            } else {
                insertIntoMap(name, file);

                logger.info(name + " successfully loaded.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isYaml(String path) {
        return path.toLowerCase().endsWith(".yml");
    }

    @SuppressWarnings("unchecked")
    public void insertIntoMap(String name, File file) {
        Map<String, Object> populatorMap = new HashMap<>();

        try {
            String fileContent = FileUtils.readFileToString(file, "UTF-8");

            if (isYaml(file.getPath())) {
                FileConfiguration config = new YamlConfiguration();
                config.loadFromString(fileContent);
                populatorMap.put("file-configuration", config);
            } else {
                populatorMap.put("file-content", fileContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        populatorMap.put("file", file);
        itemMaps.put(name, populatorMap);
    }

    public FileConfiguration getFileConfiguration(String name) {
        Object item = itemMaps.get(name).get("file-configuration");

        if (item instanceof FileConfiguration) {
            return (FileConfiguration) item;
        }

        return new YamlConfiguration();
    }

    public void clearMap() {
        itemMaps.clear();
    }
}
