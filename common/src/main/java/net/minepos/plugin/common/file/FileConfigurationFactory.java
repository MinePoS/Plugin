package net.minepos.plugin.common.file;

import net.minepos.plugin.common.file.exceptions.UnknownConfigTypeException;
import net.minepos.plugin.common.file.framework.AbstractFileConfiguration;
import net.minepos.plugin.common.file.framework.FileConfiguration;
import net.minepos.plugin.common.file.implementations.JsonFileConfiguration;
import net.minepos.plugin.common.file.implementations.SpigotFileConfiguration;
import org.apache.commons.io.FileUtils;

import java.io.File;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FileConfigurationFactory {
    public static FileConfiguration get(File file) throws Exception {
        String fileContent = FileUtils.readFileToString(file, "UTF-8");
        String[] pathBits = file.getPath().toLowerCase().split(".");

        AbstractFileConfiguration fileConfiguration;

        switch (pathBits[pathBits.length - 1]) {
            case "json": fileConfiguration = new JsonFileConfiguration(); break;
            case "yaml": fileConfiguration = new SpigotFileConfiguration(); break;
            default: throw new UnknownConfigTypeException("Unknown config type: " + pathBits[pathBits.length - 1]);
        }

        fileConfiguration.load(file, fileContent);
        return fileConfiguration;
    }
}
