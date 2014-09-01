package com.johannesbrodwall.oauth2fun.client;

import com.johannesbrodwall.oauth2fun.lib.ApplicationServer;

public class ClientApplicationServer extends ApplicationServer {

    public ClientApplicationServer(int port) {
        super(port);
    }

    public static void main(String[] args) throws Exception {
        new ClientApplicationServer(10080).start();
    }
    
}
