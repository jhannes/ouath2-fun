package com.johannesbrodwall.oauth2fun.service;

import com.johannesbrodwall.oauth2fun.lib.ApplicationServer;

public class ServiceApplicationServer extends ApplicationServer {

    public ServiceApplicationServer(int port) {
        super(port);
    }

    public static void main(String[] args) throws Exception {
        ServiceApplicationServer server = new ServiceApplicationServer(12080);
        server.addHandler(server.shutdownHandler());
        server.addHandler(server.createWebAppContext("/"));
        server.start();
    }

}
