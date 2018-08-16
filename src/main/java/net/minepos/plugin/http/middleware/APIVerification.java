package net.minepos.plugin.http.middleware;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import net.minepos.plugin.core.storage.yaml.Lang;
import net.minepos.plugin.core.storage.yaml.MFile;

/**
 * ------------------------------
 * Copyright (c) AndrewAubury 2018
 * https://www.andrewa.pw
 * Project: MinePoS
 * ------------------------------
 */
public class APIVerification extends Middleware {
    @Inject private MFile mFile;
    @Inject private Lang lang;

    @Override
    boolean canRun(HttpExchange httpExchange) {
        if(httpExchange.getRequestHeaders().containsKey("MINEPOS_AUTH")) {
            String token = mFile.getFileConfiguration("config.yml").getString("API-KEY");
            String tokenGiven = httpExchange.getRequestHeaders().get("MINEPOS_AUTH").get(0);

            if(tokenGiven.equalsIgnoreCase(token)) {
                return true;
            } else {
                returnText(httpExchange, lang.get("webserver.invalid-token"));
                return false;
            }
        } else {
            returnText(httpExchange, lang.get("webserver.no-token"));
           return false;
        }
    }
}
