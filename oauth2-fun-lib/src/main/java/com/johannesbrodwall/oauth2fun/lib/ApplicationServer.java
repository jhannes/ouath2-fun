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

    private WebAppContext createTestWebAppContext() {
        WebAppContext webAppContext = new WebAppContext("src/main/webapp", "/");
        webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        webAppContext.setConfigurations(new Configuration[] {
                new WebInfConfiguration(), new WebXmlConfiguration(),
        });
        return webAppContext;
    }

    private WebAppContext createProductionWebAppContext() {
        String war = getClass().getResource("/webapp").toExternalForm();
        WebAppContext webapp = new WebAppContext(war, "/");
        webapp.setConfigurations(new Configuration[] {
                new WebInfConfiguration(), new WebXmlConfiguration(),
        });
        return webapp;
    }

    public void start() throws Exception {
        handlers.addHandler(shutdownHandler());
        handlers.addHandler(createWebAppContext());
        server.start();
    }

    private WebAppContext createWebAppContext() {
        URL webAppUrl = getClass().getResource("/webapp");
        if (webAppUrl.getProtocol().equals("jar")) {
            return createProductionWebAppContext();
        } else {
            return createTestWebAppContext();
        }
    }

    private ShutdownHandler shutdownHandler() {
        return new ShutdownHandler("sdgsdgs", false, true);
    }

}
