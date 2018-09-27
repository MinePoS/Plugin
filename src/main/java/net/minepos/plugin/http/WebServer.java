package net.minepos.plugin.http;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpServer;
import net.minepos.plugin.core.storage.yaml.Lang;
import net.minepos.plugin.core.storage.yaml.MFile;
import net.minepos.plugin.http.route.RunCommands;
import org.bukkit.Bukkit;

import java.net.InetSocketAddress;
import java.util.logging.Logger;

/**
 * ------------------------------
 * Copyright (c) AndrewAubury 2018
 * https://www.andrewa.pw
 * Project: MinePoS
 * ------------------------------
 */
public class WebServer {
    @Inject private MFile mFile;
    @Inject private Lang lang;
    HttpServer server;
    private Logger logger;

    public WebServer() {
        logger = Bukkit.getLogger();
    }

    public void startServer() {
        try {
            int port = mFile.getFileConfiguration("config").getInt("http.port", 8391);

            logger.info(lang.get("webserver.start-message"));

            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/runcommand", new RunCommands());
            server.setExecutor(null);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer(){
       // server.stop(0);
    }
}
