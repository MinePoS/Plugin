package net.minepos.plugin.common.minepos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.minepos.plugin.common.file.GFile;
import net.minepos.plugin.common.file.framework.FileConfiguration;
import net.minepos.plugin.common.file.implementations.JsonFileConfiguration;
import net.minepos.plugin.common.minepos.exceptions.MineposConnectionException;
import net.minepos.plugin.common.minepos.objects.Category;
import net.minepos.plugin.common.minepos.params.APIParamManager;
import net.minepos.plugin.common.minepos.params.framework.APIParams;
import net.minepos.plugin.common.plugin.MineposPlugin;
import net.minepos.plugin.common.utils.minepos.WebUtils;

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
@Singleton
public final class MineposAPI {
    @Inject private GFile gFile;
    @Inject private APIParamManager paramManager;
    @Inject private MineposPlugin main;

    @Getter private MineposAPIOptions options;

    // No, multimap isn't a better solution for this, the integer needs to be equal with the id on the web side of things, multimap doesn't allow that.
    private Map<APIParams, Map<Integer, Object>> api;
    private String URL;

    public void connect(MineposAPIOptions options) throws MineposConnectionException {
        this.options = options;

        FileConfiguration config = gFile.getFileConfiguration("config");
        String URL = config.getString("api.url");
        URL = (URL.endsWith("/") ? URL : URL + "/") + "api/";
        String key = config.getString("api.key");

        this.URL = URL + "?apikey=" + key;

        if (WebUtils.validateConnection(URL, key)) {
            main.getLogger().info("Successfully connected to the MinePoS API.");
        } else {
            throw new MineposConnectionException("Failed to connect to " + URL + " using " + key);
        }
    }

    public void populateMap() {
        Map<APIParams, Map<Integer, Object>> populator = new HashMap<>();

        for (APIParams param : APIParams.values()) {
            populator.put(param, getInfo(param));
        }

        api = new ConcurrentHashMap<>(populator);
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, Object> getInfo(APIParams param) {
        Map<Integer, Object> map = new HashMap<>();

        // replace ?apikey instead of splitting at ? in-case their url is something stupid like example.com/?minepos
        List<Object> jsonObjects = (List<Object>) WebUtils.getJsonEntity(this.URL.replace("?apikey", param.getParameter() + "?apikey")).get("data", new ArrayList<>());
        List<JsonFileConfiguration> data = (jsonObjects == null || jsonObjects.isEmpty()) ? new ArrayList<>() : jsonObjects.stream().map(d -> new JsonFileConfiguration((Map<String, Object>) d)).collect(Collectors.toList());

        for (JsonFileConfiguration json : data) {
            map.put(json.getInt("id"), paramManager.run(param,
                    WebUtils.getJsonEntity(this.URL.replace("?apikey", param.getIndividual() + "/" + json.getInt("id") + "/?apikey"))));
        }

        return map;
    }

    private Object get(APIParams param, int... id) {
        if (options.isCache()) {
            return id.length >= 1 ? api.get(param).get(id[0]) : api.get(param);
        } else {
            if (id.length >= 1) {
                return paramManager.run(APIParams.CATEGORIES, WebUtils.getJsonEntity(this.URL.replace("?apikey", param.getIndividual() + "/" + id[0] + "/?apikey")));
            } else {
                return getInfo(APIParams.CATEGORIES);
            }
        }
    }

    public Category getCategory(int id) {
        return (Category) get(APIParams.CATEGORIES, id);
    }

    @SuppressWarnings("unchecked")
    public Map<Integer, Category> getCategories() {
        return (Map<Integer, Category>) get(APIParams.CATEGORIES);
    }
}
