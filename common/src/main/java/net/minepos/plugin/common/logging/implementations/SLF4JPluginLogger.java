package net.minepos.plugin.common.logging.implementations;

import lombok.Builder;
import net.minepos.plugin.common.logging.PluginLogger;
import org.slf4j.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Builder
public final class SLF4JPluginLogger implements PluginLogger {
    private Logger logger;
    private String prefix;

    private SLF4JPluginLogger(Logger logger, String prefix) {
        this.logger = logger;
        this.prefix = prefix == null ? "" : prefix;
    }

    @Override
    public void info(String str) {
        logger.info(prefix + str);
    }

    @Override
    public void warn(String str) {
        logger.warn(prefix + str);
    }

    @Override
    public void severe(String str) {
        logger.error(prefix + str);
    }
}
