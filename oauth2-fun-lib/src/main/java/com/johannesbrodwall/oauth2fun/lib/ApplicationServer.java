package com.johannesbrodwall.oauth2fun.lib;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ShutdownHandler;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

import java.net.URL;

public class ApplicationServer {

    private Server server;
    private HandlerList handlers = new HandlerList();

    public ApplicationServer(int port) {
        server = new Server(port);
        server.setHandler(handlers);
    }

    public void start() throws Exception {
        handlers.addHandler(shutdownHandler());
        handlers.addHandler(createWebAppContext());
        server.start();
    }

    private WebAppContext createWebAppContext() {
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");

        URL webAppUrl = getClass().getResource("/webapp");
        if (webAppUrl.getProtocol().equals("jar")) {
            webapp.setWar(webAppUrl.toExternalForm());
        } else {
            webapp.setWar("src/main/webapp");
            // Avoid locking static content when running exploded
            webapp.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        }
        webapp.setConfigurations(new Configuration[] {
                new WebInfConfiguration(), new WebXmlConfiguration(),
        });
        return webapp;
    }

    private ShutdownHandler shutdownHandler() {
        return new ShutdownHandler("sdgsdgs", false, true);
    }

}
