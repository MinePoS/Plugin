package net.minepos.plugin;

import com.google.inject.Inject;
import lombok.Getter;
import net.minepos.plugin.core.enums.APIKeys;
import net.minepos.plugin.core.managers.APIParamManager;
import net.minepos.plugin.core.objects.file.JFileConfiguration;
import net.minepos.plugin.core.objects.mineposapi.MineposAPIOptions;
import net.minepos.plugin.core.objects.mineposapi.MineposConnectionException;
import net.minepos.plugin.core.storage.file.GFile;
import net.minepos.plugin.core.utils.http.WebUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MineposAPI {
    @Inject private GFile gFile;
    @Inject private APIParamManager paramManager;

    @Getter private MineposAPIOptions options;
    private final Map<APIKeys, Map<Integer, Object>> api = new ConcurrentHashMap<>();
    private String URL;

    public void connect(MineposAPIOptions options) throws MineposConnectionException {
        this.options = options;

        FileConfiguration config = gFile.getFileConfiguration("config");
        String URL = config.getString("api.url");
        URL = (URL.endsWith("/") ? URL : URL + "/") + "api/";
        String key = config.getString("api.key");

        this.URL = URL + "?apikey=" + key;

        if (WebUtils.validateKeyAndURL(URL, key)) {
            System.out.println("success!");
        } else {
            throw new MineposConnectionException("Failed to connect to " + URL + " using " + key);
        }

        populateMap();
    }

    @SuppressWarnings("unchecked")
    private void populateMap() {
        for (APIKeys key : APIKeys.values()) {
            // replace ?apikey instead of splitting at ? in-case their url is something stupid like example.com/?minepos
            List<JFileConfiguration> data = ((List<Object>) WebUtils.getJsonEntity(this.URL.replace("?apikey", key.getParameter() + "?apikey")).get("data", new ArrayList<>()))
                    .stream().map(new JFileConfiguration());

            JFileConfiguration json = new JFileConfiguration((Map<String, Object>) obj);
            Map<Integer, Object> populatorMap = new HashMap<>();

            populatorMap.put(data.getInt("id"), paramManager.run(key, data));
            api.put(key, populatorMap);
        }

        System.out.println(api);
    }
}
