package com.johannesbrodwall.oauth2fun.client;

import com.johannesbrodwall.oauth2fun.lib.ApplicationServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientApplicationServer extends ApplicationServer {

    public ClientApplicationServer(int port) {
        super(port);
    }

    public static void main(String[] args) throws Exception {
        ClientApplicationServer server = new ClientApplicationServer(10080);
        server.addHandler(server.shutdownHandler());
        server.addHandler(server.createWebAppContext("/client"));
        server.addHandler(server.createRedirectContextHandler("/", "/client"));
        server.start();

        log.info("Started " + server.getURI());
    }


}
