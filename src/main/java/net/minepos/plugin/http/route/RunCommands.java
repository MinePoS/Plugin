package net.minepos.plugin.http.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.minepos.plugin.MinePoS;
import net.minepos.plugin.core.objects.CommandNotification;
import net.minepos.plugin.core.objects.MinePoSSender;
import net.minepos.plugin.http.middleware.APIVerification;
import net.minepos.plugin.http.middleware.Middleware;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ------------------------------
 * Copyright (c) AndrewAubury 2018
 * https://www.andrewa.pw
 * Project: MinePoS
 * ------------------------------
 */
public class RunCommands implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) {
        // returnText(httpExchange, "test");
        try {
        new APIVerification().setHandler(new Runnable() {
            @Override
            public void run() {

            if (httpExchange.getRequestURI().getQuery() == null || httpExchange.getRequestURI().getQuery() == "") {
                returnText(httpExchange, "Bad query");
                return;
            }
            Map<String, String> query = queryToMap(httpExchange.getRequestURI().getQuery());
            Bukkit.getLogger().info("Passed API Check!");
            JSONObject jo = new JSONObject();
            jo.put("code", 1);
            jo.put("status", "Server got command request");

            String command = query.get("command");

            byte[] decodedBytes = Base64.getDecoder().decode(command);
            command = new String(decodedBytes);

            UUID uuid = null;
            if (query.containsKey("uuid")) {
                uuid = UUID.fromString(query.get("uuid"));
            }

            CommandNotification cn = new CommandNotification(uuid, query.get("QueueID"), command);
            cn.runTask(JavaPlugin.getProvidingPlugin(MinePoS.class));

            returnText(httpExchange, jo.toJSONString());

    }
    }).check(httpExchange);

        } catch (Exception e) {
            returnText(httpExchange, e.getLocalizedMessage());
        }
    }

    public void returnText(HttpExchange t, String text) {

        try {
            byte [] response = text.getBytes();
            t.sendResponseHeaders(200, response.length);

            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
