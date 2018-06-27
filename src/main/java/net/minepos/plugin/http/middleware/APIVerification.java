package net.minepos.plugin.http.middleware;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import net.minepos.plugin.core.storage.yaml.MFile;

import java.io.IOException;

/**
 * ------------------------------
 * Copyright (c) AndrewAubury 2018
 * https://www.andrewa.pw
 * Project: MinePoS
 * ------------------------------
 */
public class APIVerification extends Middleware{
    @Inject private MFile mFile;

    @Override
    boolean canRun(HttpExchange httpExchange){
        if(httpExchange.getRequestHeaders().containsKey("MINEPOS_AUTH")){
            String token = mFile.getFileConfiguration("config.yml").getString("API-KEY");
            String tokenGiven = httpExchange.getRequestHeaders().get("MINEPOS_AUTH").get(0);
            if(tokenGiven.equalsIgnoreCase(token)){
                return true;
            }else{
                returnText(httpExchange, "The MinePoS API-KEY was not valid");
                return false;
            }
        }else{
            returnText(httpExchange, "No MinePoS API-KEY was provided");
           return false;
        }
    }
}
