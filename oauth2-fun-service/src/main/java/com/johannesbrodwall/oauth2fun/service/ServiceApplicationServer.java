package com.johannesbrodwall.oauth2fun.service;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.johannesbrodwall.oauth2fun.lib.ApplicationServer;

public class ServiceApplicationServer extends ApplicationServer {

    public ServiceApplicationServer(int port) {
        super(port);
    }

    public static void main(String[] args) throws Exception {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        ServiceApplicationServer server = new ServiceApplicationServer(12080);
        server.addHandler(server.shutdownHandler());
        server.addHandler(server.createWebAppContext("/"));
        server.start();
    }

}
