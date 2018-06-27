package net.minepos.plugin.http;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpServer;
import net.minepos.plugin.core.storage.yaml.MFile;
import net.minepos.plugin.http.route.RunCommands;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * ------------------------------
 * Copyright (c) AndrewAubury 2018
 * https://www.andrewa.pw
 * Project: MinePoS
 * ------------------------------
 */
public class WebServer {
    HttpServer server;
    @Inject private MFile mFile;

    public void startServer() throws IOException {
        LoggerFactory.getLogger("MinePoS-Web").info("STARTING");
        server = HttpServer.create(new InetSocketAddress(mFile.getFileConfiguration("http.yml").getInt("port")), 0);
        server.createContext("/runcommand", new RunCommands());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
