package net.minepos.plugin;

import fi.iki.elonen.NanoWSD;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class WebSocketManager {
    private final NanoWSD ws = new WebSocketImpl();

    public void start() {
        try {
            ws.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        ws.stop();
    }
}
