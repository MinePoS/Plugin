package net.minepos.plugin.common.utils.minepos;

import net.minepos.plugin.common.file.implementations.JsonFileConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class WebUtils {
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

    public static JsonFileConfiguration getJsonEntity(String URL) {
        JsonFileConfiguration json = new JsonFileConfiguration();
        json.load(null, getStringEntity(URL));
        return json;
    }

    public static boolean validateConnection(String URL, String key) {
        JsonFileConfiguration json;

        try {
            json = getJsonEntity(URL + "/?apikey=" + key);
        } catch (Exception e) {
            return false;
        }

        return json.getBoolean("success", false);
    }
}
