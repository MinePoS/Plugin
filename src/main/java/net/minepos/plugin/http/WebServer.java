package net.minepos.plugin.http;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpServer;
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

    private Logger logger;

    public WebServer() {
        logger = Bukkit.getLogger();
    }

    public boolean startServer() {
        try {
            if(mFile.getFileConfiguration("config").getBoolean("websocket.use-websocket") == false) {
                logger.info("STARTING WEBSERVER");
                logger.info("Webserver on port: " + String.valueOf(mFile.getFileConfiguration("http").getInt("port")));
    
                HttpServer server = HttpServer.create(new InetSocketAddress(mFile.getFileConfiguration("http").getInt("port")), 0);
                server.createContext("/runcommand", new RunCommands());
                server.setExecutor(null); // creates a default executor
                server.start();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
