package com.johannesbrodwall.oauth2fun.ident;

import com.johannesbrodwall.oauth2fun.lib.ApplicationServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdentApplicationServer extends ApplicationServer {

    public IdentApplicationServer(int port) {
        super(port);
    }


    public static void main(String[] args) throws Exception {
        IdentApplicationServer server = new IdentApplicationServer(11080);
        server.addHandler(server.shutdownHandler());
        server.addHandler(server.createWebAppContext("/ident"));
        server.addHandler(server.createRedirectContextHandler("/", "/ident"));
        server.start();

        log.info("Started " + server.getURI());
    }

}
