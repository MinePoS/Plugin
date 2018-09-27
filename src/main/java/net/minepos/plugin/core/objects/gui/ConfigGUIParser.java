package net.minepos.plugin.core.objects.gui;

import com.google.inject.Inject;
import net.minepos.plugin.core.handlers.GUIHandler;
import net.minepos.plugin.core.utils.string.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class ConfigGUIParser {
    @Inject private static GUIHandler guiHandler;

    private static GUIItem parseItem(ConfigurationSection section) {
        GUIItemBuilder guiItemBuilder = new GUIItemBuilder(
                Integer.parseInt(section.getName()),
                section.getStringList("description"),
                section.getString("name", "null")
        );

        String material = section.getString("material", "BEDROCK").toUpperCase();
        GUIItemMaterial guiItemMaterial = new GUIItemMaterial(section.getInt("slot", 1));

        if (material.startsWith("HEAD-")) {
            guiItemMaterial.setMaterial(Material.SKULL_ITEM);
            guiItemBuilder.setSkullOwner(UUID.fromString(material.toLowerCase().split("-")[1]));
        } else {
            guiItemMaterial.setMaterial(Material.valueOf(material));
        }

        return guiItemBuilder.setGuiItemMaterial(guiItemMaterial).build();
    }

    public static void parseGUI(final FileConfiguration config, String key) {
        List<GUIItem> items = new ArrayList<>();
        ConfigurationSection itemsSection = config.getConfigurationSection("items");

        for (String str : itemsSection.getKeys(false)) {
            items.add(parseItem(itemsSection.getConfigurationSection(str)));
        }

        guiHandler.getGuis().put(key, new GUIBuilder(config.getInt("slots", 54), StringUtils.cc(config.getString("title", "null")), items).build());
    }
}
