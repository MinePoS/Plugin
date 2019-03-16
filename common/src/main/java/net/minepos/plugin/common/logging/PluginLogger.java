package net.minepos.plugin.common.logging;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public interface PluginLogger {
    void info (String s);

    void warn(String s);

    void severe(String s);
}
