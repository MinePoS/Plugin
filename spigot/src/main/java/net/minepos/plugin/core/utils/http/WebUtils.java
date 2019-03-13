package net.minepos.plugin.core.utils.http;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.inject.Inject;
import net.minepos.plugin.core.objects.file.JFileConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class WebUtils {
    @Inject private static Gson gson;

    public static String getStringEntity(String URL) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(URL);

        try {
            HttpResponse response = client.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @SuppressWarnings("unchecked")
    public static JFileConfiguration getJsonEntity(String URL) {
        return new JFileConfiguration(gson.fromJson(getStringEntity(URL), LinkedTreeMap.class));
    }

    public static boolean validateKeyAndURL(String URL, String apiKey) {
        JFileConfiguration json;

        try {
            json = getJsonEntity(URL + "/?apikey=" + apiKey);
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            json = new JFileConfiguration(map);
        }

        return json.getBoolean("success", false);
    }
}
