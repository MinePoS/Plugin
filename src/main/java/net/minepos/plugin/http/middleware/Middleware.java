package net.minepos.plugin.http.middleware;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * ------------------------------
 * Copyright (c) AndrewAubury 2018
 * https://www.andrewa.pw
 * Project: MinePoS
 * ------------------------------
 */
public abstract class Middleware {
    private Middleware nextMiddleware;
    private Runnable handler;

    abstract boolean canRun(HttpExchange exchange);

    public Middleware setHandler(Runnable h) {
        handler = h;
        return this;
    }

    public Middleware setAndGetNext(Middleware m) {
        nextMiddleware = m;
        return nextMiddleware;
    }

    public void check(HttpExchange exchange) {
        if (canRun(exchange)) {
            if (nextMiddleware == null) {
                    handler.run();
            } else {
                nextMiddleware.setHandler(handler);
                nextMiddleware.check(exchange);
            }
        }
    }

    public Middleware(Runnable h) {
        setHandler(h);
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
    public Middleware() {

    }
}
