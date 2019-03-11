package net.minepos.plugin;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import net.minepos.plugin.core.enums.APIKeys;
import net.minepos.plugin.core.managers.APIParamManager;
import net.minepos.plugin.core.objects.file.JFileConfiguration;
import net.minepos.plugin.core.objects.mineposapi.MineposAPIOptions;
import net.minepos.plugin.core.objects.mineposapi.MineposConnectionException;
import net.minepos.plugin.core.objects.mineposapi.objects.Category;
import net.minepos.plugin.core.storage.file.GFile;
import net.minepos.plugin.core.utils.http.WebUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MineposAPI {
    @Inject private GFile gFile;
    @Inject private APIParamManager paramManager;

    @Getter private MineposAPIOptions options;
    // guava multimap would be perfect for this, if only the ids automatically re-adjusted on the web side of things.
    private Map<APIKeys, Map<Integer, Object>> api;
    private String URL;
    @Setter private boolean cache = false;

    public void connect(MineposAPIOptions options) throws MineposConnectionException {
        this.options = options;

        FileConfiguration config = gFile.getFileConfiguration("config");
        String URL = config.getString("api.url");
        URL = (URL.endsWith("/") ? URL : URL + "/") + "api/";
        String key = config.getString("api.key");

        this.URL = URL + "?apikey=" + key;

        if (WebUtils.validateKeyAndURL(URL, key)) {
            Bukkit.getLogger().info("Successfully connected to the MinePoS API.");
        } else {
            throw new MineposConnectionException("Failed to connect to " + URL + " using " + key);
        }
    }

    public void populateMap() {
        Map<APIKeys, Map<Integer, Object>> populator = new HashMap<>();

        for (APIKeys key : APIKeys.values()) {
            populator.put(key, getInfo(key));
        }

        api = new ConcurrentHashMap<>(populator);
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, Object> getInfo(APIKeys key) {
        Map<Integer, Object> map = new HashMap<>();

        // replace ?apikey instead of splitting at ? in-case their url is something stupid like example.com/?minepos
        List<Object> jsonObjects = (List<Object>) WebUtils.getJsonEntity(this.URL.replace("?apikey", key.getParameter() + "?apikey")).get("data", new ArrayList<>());
        List<JFileConfiguration> data = (jsonObjects == null || jsonObjects.isEmpty()) ? new ArrayList<>() : jsonObjects.stream().map(d -> new JFileConfiguration((Map<String, Object>) d)).collect(Collectors.toList());

        for (JFileConfiguration json : data) {
            map.put(json.getInt("id"), paramManager.run(key,
                    WebUtils.getJsonEntity(this.URL.replace("?apikey", key.getIndividual() + "/" + json.getInt("id") + "/?apikey"))));
        }

        return map;
    }

    private Object get(APIKeys key, int... id) {
        if (cache) {
            return id.length >= 1 ? api.get(key).get(id[0]) : api.get(key);
        } else {
            if (id.length >= 1) {
                return paramManager.run(APIKeys.CATEGORIES, WebUtils.getJsonEntity(this.URL.replace("?apikey", key.getIndividual() + "/" + id[0] + "/?apikey")));
            } else {
                return getInfo(APIKeys.CATEGORIES);
            }
        }
    }

    public Category getCategory(int id) {
        return (Category) get(APIKeys.CATEGORIES, id);
    }

    @SuppressWarnings("unchecked")
    public Map<Integer, Category> getCategories() {
        return (Map<Integer, Category>) get(APIKeys.CATEGORIES);
    }
}
