package net.minepos.plugin;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.inject.Inject;
import lombok.Getter;
import net.minepos.plugin.core.enums.APIKeys;
import net.minepos.plugin.core.objects.file.JFileConfiguration;
import net.minepos.plugin.core.objects.mineposapi.MineposAPIOptions;
import net.minepos.plugin.core.objects.mineposapi.MineposConnectionException;
import net.minepos.plugin.core.storage.file.GFile;
import net.minepos.plugin.core.utils.http.WebUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MineposAPI {
    @Inject private Gson gson;
    @Inject private GFile gFile;

    @Getter private MineposAPIOptions options;
    private final Map<APIKeys, Map<String, Object>> api = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public void connect(MineposAPIOptions options) throws MineposConnectionException {
        this.options = options;

        FileConfiguration config = gFile.getFileConfiguration("config");
        JFileConfiguration json = new JFileConfiguration(gson.fromJson(WebUtils.getStringEntity(config.getString("api.url") + "/api/?apikey=" + config.getString("api.key")), LinkedTreeMap.class));

        if (json.getBoolean("success", false)) {
            System.out.println("success!");
        } else {
            throw new MineposConnectionException("Failed to connect to " + config.getString("api.url") + " using " + config.getString("api.key"));
        }
    }

//    private void populateMap() {
//        for (String param : Arrays.stream(APIKeys.values()).map(APIKeys::getParameter).collect(Collectors.toList())) {
//
//        }
//    }
}
