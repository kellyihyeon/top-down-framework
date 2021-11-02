package com.github.kelly.core;

import com.github.kelly.mvc.*;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.reflections.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Container {

    private Server server;
    private int port = 8080;

    private final Map<String, Set<Class<?>>> containerMap = new HashMap<>();
    private final Map<RequestKey, String> handlerMap = new HashMap<>();
    private final Map<RequestKey, RequestHandler> handlerMap2 = new HashMap<>();


    public void getComponentScan(Class<?> primarySource) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        // primarySource = Application.class
        final ComponentScan componentScan = primarySource.getAnnotation(ComponentScan.class);
        // "com.github.kelly.app"
        final Reflections reflections = new Reflections(componentScan.basePackage());
        // @Controller 뽑기
        final Set<Class<?>> controllerScannedClazz = reflections.getTypesAnnotatedWith(Controller.class);
        containerMap.put("controller", controllerScannedClazz);
        System.out.println("containerMap.get(\"controller\") = " + containerMap.get("controller"));

        // @RequestMapping 정보 뽑기
        final Set<Class<?>> controllerBeans = containerMap.get("controller");
        for (Class<?> controllerBean : controllerBeans) {
            System.out.println("controllerBean.getName() = " + controllerBean.getName());
            final Object instance = controllerBean.getDeclaredConstructor().newInstance();
            final Method[] methods = controllerBean.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    final RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

                    final RequestKey requestKey = new RequestKey(requestMapping.value(), requestMapping.method());
                    RequestHandler requestHandler = context -> context.response().execute(method, instance);
                    handlerMap2.put(requestKey, requestHandler);
                }
                
            }

        } //

    }

    // happy path
    public void start() {

        // server start
        this.server = new Server(port);

        final ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);    // 1

        contextHandler.setContextPath("/hello");
        server.setHandler(contextHandler);

        contextHandler.addServlet(new ServletHolder(new HelloServlet()), "/*");

        try {
            this.server.start();
            this.server.join();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
