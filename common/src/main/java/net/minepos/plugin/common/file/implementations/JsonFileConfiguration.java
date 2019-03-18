package net.minepos.plugin.common.file.implementations;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.inject.Inject;
import net.minepos.plugin.common.file.framework.AbstractFileConfiguration;
import net.minepos.plugin.common.file.framework.FileConfiguration;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
//TODO: eager load instead of lazy load
public final class JsonFileConfiguration extends AbstractFileConfiguration {
    @Inject private Gson gson;

    private static final String NULL_STRING = "null";
    private static final int NULL_NUM = 0;
    private static final boolean NULL_BOOL = false;

    private Map<String, Object> items;

    public JsonFileConfiguration() {}

    private JsonFileConfiguration(Map<String, Object> items) {
        this.items = items;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void internalLoad(File file, String fileContent) {
        items = gson.fromJson(fileContent, LinkedTreeMap.class);
    }

    @Override
    public Object get(String path, Object... def) {
        Object object = itemMap.getOrDefault(path, null);

        if (path.contains(".") && !path.startsWith(".") && !path.endsWith(".")) {
            String[] areas = path.split("\\.");
            object = items.getOrDefault(areas[0], null);

            if (areas.length >= 2 && object != null) {
                object = getBuriedObject(areas);
            }
        }

        return value(object, null, def);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FileConfiguration getConfigSection(String path, FileConfiguration... def) {
        Object object = get(path, (Object[]) def);

        if (isConfigSection(object)) {
            return new JsonFileConfiguration((Map<String, Object>) object);
        }

        return value(null, new JsonFileConfiguration(), def);
    }

    @Override
    public String getString(String path, String... def) {
        Object object = get(path, (Object[]) def);

        if (object instanceof String) {
            return (String) object;
        }

        return value(null, NULL_STRING, def);
    }

    @Override
    public int getInt(String path, Integer... def) {
        return (int) getDouble(path, ArrayUtils.toObject(Arrays.stream(ArrayUtils.toPrimitive(def)).asDoubleStream().toArray()));
    }

    @Override
    public long getLong(String path, Long... def) {
        return (long) getDouble(path, ArrayUtils.toObject(Arrays.stream(ArrayUtils.toPrimitive(def)).asDoubleStream().toArray()));
    }

    @Override
    public double getDouble(String path, Double... def) {
        Object object = get(path, (Object[]) def);

        if (object instanceof Double) {
            return (double) object;
        }

        return value(null, (double) NULL_NUM, def);
    }

    @Override
    public boolean getBoolean(String path, Boolean... def) {
        Object object = get(path, (Object[]) def);

        if (object instanceof Boolean) {
            return (boolean) object;
        }

        return value(null, NULL_BOOL, def);
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    @Override
    public final List<String> getStringList(String path, List<String>... def) {
        Object object = get(path, (Object[]) def);

        if (object instanceof List<?>) {
            for (Object obj : (List<?>) object) {
                if (obj instanceof String) {
                    return (List<String>) object;
                }
            }
        }

        return value(null, new ArrayList<>(), def);
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    @Override
    public final List<FileConfiguration> getConfigList(String path, List<FileConfiguration>... def) {
        Object object = get(path, (Object[]) def);

        if (object instanceof List<?>) {
            List<FileConfiguration> configs = new ArrayList<>();

            for (Object obj : (List<?>) object) {
                if (isConfigSection(obj)) {
                    configs.add(new JsonFileConfiguration((Map<String, Object>) obj));
                }
            }

            if (configs.size() >= 1) {
                return configs;
            }
        }

        return value(null, new ArrayList<>(), def);
    }

    @SafeVarargs
    @Override
    public final List<?> getList(String path, List<Object>... def) {
        Object object = get(path, (Object[]) def);

        if (object instanceof List<?>) {
            return (List<?>) object;
        }

        return value(null, new ArrayList<>(), def);
    }

    private boolean isConfigSection(Object object) {
        if (object instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) object;

            if (map.size() >= 1) {
                return map.keySet().toArray()[0] instanceof String && map.values().toArray()[0] != null;
            }
        }

        return false;
    }
}
