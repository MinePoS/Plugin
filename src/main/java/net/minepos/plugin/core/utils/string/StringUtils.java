package net.minepos.plugin.core.utils.string;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class StringUtils {
    public static boolean startsWith(String str, String contain) {
        return Arrays.asList(contain.toLowerCase().split("/")).parallelStream().anyMatch(str.toLowerCase()::startsWith);
    }

    public static String cc(String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }

    public static List<String> cc(List<String> list) {
        List<String> coloured = new ArrayList<>();
        list.forEach(str -> coloured.add(cc("&r" + str)));

        return coloured;
    }

    public static List<String> format(List<String> list, Object... placeholders) {
        List<String> formatted = new ArrayList<>();
        list.stream().map(StringUtils::cc).forEach(str -> list.add(String.format(str, placeholders)));

        return formatted;
    }
}