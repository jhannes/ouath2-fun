package com.johannesbrodwall.oauth2fun.ident;

import com.johannesbrodwall.oauth2fun.lib.ApplicationServer;

public class IdentApplicationTestServer extends ApplicationServer {

    public IdentApplicationTestServer(int port) {
        super(port);
    }


    public static void main(String[] args) throws Exception {
        new IdentApplicationTestServer(11080).startTest();
    }
    
}
