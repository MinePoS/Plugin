package net.minepos.plugin.common.file.implementations;

import net.minepos.plugin.common.file.framework.AbstractFileConfiguration;
import net.minepos.plugin.common.file.framework.FileConfiguration;
import net.minepos.plugin.common.json.JsonParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class JsonFileConfiguration extends AbstractFileConfiguration {
    private static final String NULL_STRING = "null";
    private static final int NULL_NUM = 0;
    private static final boolean NULL_BOOL = false;

    private JsonParser parser;

    public JsonFileConfiguration() {}

    private JsonFileConfiguration(JsonParser parser) {
        this.parser = parser;
    }

    @Override
    protected void internalLoad(File file, String fileContent) {
        parser = new JsonParser(fileContent);
    }

    @Override
    public Object get(String path, Object... def) {
        return value(parser.get(path), null, def);
    }

    @Override
    public FileConfiguration getConfigSection(String path, FileConfiguration... def) {
        return value(new JsonFileConfiguration(parser.getJsonSection(path)), new JsonFileConfiguration(), def);
    }

    @Override
    public String getString(String path, String... def) {
        return value(parser.getString(path), NULL_STRING, def);
    }

    @Override
    public int getInt(String path, Integer... def) {
        return value(parser.getInt(path), NULL_NUM, def);
    }

    @Override
    public long getLong(String path, Long... def) {
        return value(parser.getLong(path), (long) NULL_NUM, def);
    }

    @Override
    public double getDouble(String path, Double... def) {
        return value(parser.getDouble(path), (double) NULL_NUM, def);
    }

    @Override
    public boolean getBoolean(String path, Boolean... def) {
        return value(parser.getBoolean(path), NULL_BOOL, def);
    }

    @SafeVarargs
    @Override
    public final List<String> getStringList(String path, List<String>... def) {
        return value(parser.getStringList(path), new ArrayList<>(), def);
    }

    @SafeVarargs
    @Override
    public final List<FileConfiguration> getConfigList(String path, List<FileConfiguration>... def) {
        List<JsonParser> jsons = parser.getJsonList(path);

        if (jsons == null) {
            return value(null, new ArrayList<>(), def);
        }

        //noinspection ConstantConditions
        return value(parser.getJsonList(path).stream().map(JsonFileConfiguration::new).collect(Collectors.toList()), new ArrayList<>(), def);
    }

    @SafeVarargs
    @Override
    public final List<?> getList(String path, List<Object>... def) {
        return value(parser.getList(path), new ArrayList<>(), def);
    }
}
