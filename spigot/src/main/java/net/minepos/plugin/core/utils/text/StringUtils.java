package net.minepos.plugin.core.utils.text;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class StringUtils {
    public static boolean startsWith(String str, String[] elements) {
        return lowercaseStream(Arrays.asList(elements)).anyMatch(str.toLowerCase()::startsWith);
    }

    public static String colour(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private static Stream<String> lowercaseStream(List<String> list) {
        return list.stream().map(String::toLowerCase);
    }
}
