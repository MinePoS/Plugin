package net.minepos.plugin.http;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.minepos.plugin.core.handlers.HTTPHandler;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.Response;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class WebServer extends NanoHTTPD {
    public static final String PUB =
            "removed";

    private final HTTPHandler httpHandler;

    public WebServer(int port, HTTPHandler httpHandler) {
        super(port);

        this.httpHandler = httpHandler;

        try {
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> params = new HashMap<>();
        Method method = session.getMethod();

        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                session.parseBody(params);
            } catch (Exception e) {
                return Response.newFixedLengthResponse("it didn't work");
            }
        }

        Map<String, List<String>> headers = session.getParameters();

//        System.out.println(headers);

        if (headers.containsKey("api-key")) {
            if (!match(headers.get("api-key").get(0))) {
                return Response.newFixedLengthResponse("Incorrect private key");
            }
        } else {
            return Response.newFixedLengthResponse("key header missing");
        }

        httpHandler.run(session.getUri(), session.getParameters());
        return Response.newFixedLengthResponse("it worked");
    }

    private boolean match(String priv) {
        System.out.println(priv);
        try {
            //todo: make this work
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] pkcs8keyPB = decoder.decode(PUB);
            byte[] pkcs8keyPV = decoder.decode(priv);
            PKCS8EncodedKeySpec pubSpec = new PKCS8EncodedKeySpec(pkcs8keyPB);
            PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(pkcs8keyPV);
            KeyFactory factory = KeyFactory.getInstance("ECDSA");
            PrivateKey privKey = factory.generatePrivate(privSpec);
            PublicKey pubKey = factory.generatePublic(pubSpec);

            System.out.println(((ECPrivateKey) privKey).getParams());
            System.out.println(((ECPublicKey) pubKey).getParams());

            return ((ECPrivateKey) privKey).getParams().equals(((ECPublicKey) pubKey).getParams());
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return false;
    }
}
