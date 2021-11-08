package com.github.kelly.web;

import com.github.kelly.core.WebServer;
import org.eclipse.jetty.server.Server;

public class JettyWebServer implements WebServer {

    private final int port;
    private final Server server;
    private final JettyHandler jettyHandler;


    public JettyWebServer(int port, DispatcherServlet dispatcherServlet) {
        this.port = port;
        this.server = new Server(port);
        this.jettyHandler = new JettyHandler(dispatcherServlet);
    }


    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void start() throws Exception {
        server.setHandler(jettyHandler);
        server.start();
        server.join();
    }
}
