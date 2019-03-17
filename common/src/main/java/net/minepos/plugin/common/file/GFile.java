package net.minepos.plugin.common.file;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.minepos.plugin.common.file.framework.FileConfiguration;
import net.minepos.plugin.common.logging.PluginLogger;
import net.minepos.plugin.common.plugin.MineposPlugin;
import net.minepos.plugin.common.utils.FileUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class GFile {
    @Inject private MineposPlugin main;

    private final Map<String, FileConfiguration> configs = new ConcurrentHashMap<>();
    private final PluginLogger logger = main.getLogger();

    public void make(String name, String externalPath, String internalPath) {
        File file = new File(externalPath);

        try {
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.getParentFile().mkdirs();

                if (file.createNewFile()) {
                    if (FileUtils.exportResource(MineposPlugin.class.getResourceAsStream(internalPath), externalPath)) {
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

    @SuppressWarnings("unchecked")
    public void insertIntoMap(String name, File file) {
        try {
            configs.put(name, FileConfigurationFactory.get(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getFileConfiguration(String name) {
        return configs.get(name);
    }

    public void clearMap() {
        configs.clear();
    }
}
