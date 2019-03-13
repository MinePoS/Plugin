package net.minepos.plugin.core.storage.file;

import com.google.inject.Inject;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class GLang {
    @Inject private static GFile gFile;

    public static String getString(String key, Object... args) {
        return String.format(gFile.getFileConfiguration("lang").getString(key, "null"), args);
    }
}
