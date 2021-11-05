package com.github.kelly.core;

import com.github.kelly.mvc.*;
import org.eclipse.jetty.server.Server;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Container {

    private Server server;
    private int port = -1;

    private final Map<RequestKey, RequestHandler> handlerMap = new HashMap<>();


    public void configurePort(int port) {
        this.port = port;
    }

    public Map<RequestKey, RequestHandler> getHandlerMap() {
        return handlerMap;
    }

    public void getComponentScan(Class<?> primarySource) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ComponentScanner scanner = new ComponentScanner(primarySource);
        scanner.scan();
        scanner.addKeyAndHandlerToMap(handlerMap);
    }


    public void start() {

        int serverPort = 8080;
        if (port >= 1) {
            serverPort = port;
        }
        this.server = new Server(serverPort);

        final HttpHandler httpHandler = new HttpHandler(handlerMap);
        this.server.setHandler(httpHandler);

        try {
            this.server.start();
            this.server.join();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
