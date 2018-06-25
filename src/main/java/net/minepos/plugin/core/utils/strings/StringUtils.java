package net.minepos.plugin.core.utils.strings;

import java.util.Arrays;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class StringUtils {
    public boolean startsWith(String msg, String str) {
        if (str.contains("/")) {
            String[] contain = str.toLowerCase().split("/");
            return Arrays.stream(contain).anyMatch(msg.toLowerCase()::startsWith);
        }

        return msg.toLowerCase().startsWith(str.toLowerCase());
    }
}
