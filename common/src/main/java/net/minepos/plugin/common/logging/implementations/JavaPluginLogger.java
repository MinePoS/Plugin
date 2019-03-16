package net.minepos.plugin.common.logging.implementations;

import lombok.Builder;
import net.minepos.plugin.common.logging.PluginLogger;

import java.util.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Builder
public final class JavaPluginLogger implements PluginLogger {
    private Logger logger;
    private String prefix;

    private JavaPluginLogger(Logger logger, String prefix) {
        this.logger = logger;
        this.prefix = prefix == null ? "" : prefix;
    }

    @Override
    public void info(String str) {
        logger.info(prefix + str);
    }

    @Override
    public void warn(String str) {
        logger.warning(prefix + str);
    }

    @Override
    public void severe(String str) {
        logger.severe(prefix + str);
    }
}
