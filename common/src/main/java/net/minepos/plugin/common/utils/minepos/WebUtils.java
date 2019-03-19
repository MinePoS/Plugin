package net.minepos.plugin.common.utils.minepos;

import net.minepos.plugin.common.json.JsonParser;
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

    public static JsonParser getJsonEntity(String URL) {
        return new JsonParser(getStringEntity(URL));
    }

    public static boolean validateConnection(String URL, String key) {
        JsonParser json;

        try {
            json = getJsonEntity(URL + "/?apikey=" + key);
        } catch (Exception e) {
            return false;
        }

        Boolean bool = json.getBoolean("success");
        return bool != null && bool;
    }
}
