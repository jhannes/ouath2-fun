package com.johannesbrodwall.oauth2fun.ident;

import com.johannesbrodwall.oauth2fun.lib.ApplicationServer;

public class IdentApplicationServer extends ApplicationServer {

    public IdentApplicationServer(int port) {
        super(port);
    }


    public static void main(String[] args) throws Exception {
        new IdentApplicationServer(11080).start();
    }
    
}
