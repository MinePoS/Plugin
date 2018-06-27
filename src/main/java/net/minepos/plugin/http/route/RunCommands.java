package net.minepos.plugin.http.route;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.minepos.plugin.http.middleware.APIVerification;
import net.minepos.plugin.http.middleware.Middleware;

import java.io.IOException;
import java.io.OutputStream;

/**
 * ------------------------------
 * Copyright (c) AndrewAubury 2018
 * https://www.andrewa.pw
 * Project: MinePoS
 * ------------------------------
 */
public class RunCommands implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        new APIVerification().setHandler(new Runnable() {
            @Override
            public void run() {
                returnText(httpExchange, "You have got to the API");
            }
        });
    }

    void returnText(HttpExchange t, String text) {

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

}
