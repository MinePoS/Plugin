package net.minepos.plugin.core.objects.gui;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class ConfigGUIParser {
    private static GUIItem parseItem(ConfigurationSection section) {
        return new GUIItemBuilder(
                new GUIItemMaterial(Material.valueOf(section.getString("material", "BEDROCK").toUpperCase()), section.getInt("slot", 1)),
                Integer.parseInt(section.getName()),
                section.getStringList("description"),
                section.getString("name", "null")
        ).build();
    }

    public static GUIBuilder parseGUI(final FileConfiguration config, Player player) {
        List<GUIItem> items = new ArrayList<>();
        ConfigurationSection itemsSection = config.getConfigurationSection("items");

        for (String str : itemsSection.getKeys(false)) {
            items.add(parseItem(itemsSection.getConfigurationSection(str)));
        }

        return new GUIBuilder(player, config.getInt("slots", 9), config.getString("title", "null"), items);
    }
}
