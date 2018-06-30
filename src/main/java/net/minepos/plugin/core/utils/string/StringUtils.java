package net.minepos.plugin.core.utils.string;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class StringUtils {
    public static boolean startsWith(String msg, String str) {
        if (str.contains("/")) {
            String[] contain = str.toLowerCase().split("/");
            return Arrays.stream(contain).anyMatch(msg.toLowerCase()::startsWith);
        }

        return msg.toLowerCase().startsWith(str.toLowerCase());
    }

    public static String arrayToString(String[] array, String delimiter) {
        return Arrays.stream(array).collect(Collectors.joining(delimiter));
    }

    public static String cc(String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }
}