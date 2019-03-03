package net.minepos.plugin;

import fi.iki.elonen.NanoWSD;

import java.io.IOException;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class WebSocketImpl extends NanoWSD {
    public WebSocketImpl() {
        super(1234);
    }

    @Override
    protected WebSocket openWebSocket(IHTTPSession ihttpSession) {
        System.out.println(ihttpSession.getUri());

        return new WebSocket(ihttpSession) {
            @Override
            protected void onOpen() {
                System.out.println("open");
            }

            @Override
            protected void onClose(WebSocketFrame.CloseCode closeCode, String s, boolean b) {
                System.out.println(closeCode.getValue() + " - " + s + " - " + b);
            }

            @Override
            protected void onMessage(WebSocketFrame webSocketFrame) {
                System.out.println(webSocketFrame.getTextPayload());
            }

            @Override
            protected void onPong(WebSocketFrame webSocketFrame) {
                System.out.println(webSocketFrame.getTextPayload());
            }

            @Override
            protected void onException(IOException e) {
                e.printStackTrace();
            }
        };
    }
}
