package com.johannesbrodwall.oauth2fun.service;

import com.johannesbrodwall.oauth2fun.lib.ApplicationServer;

public class ServiceApplicationServer extends ApplicationServer {

    public ServiceApplicationServer(int port) {
        super(port);
    }
    
    public static void main(String[] args) throws Exception {
        new ServiceApplicationServer(12080).start();
    }

}
