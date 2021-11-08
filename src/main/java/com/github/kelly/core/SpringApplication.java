package com.github.kelly.core;

import com.github.kelly.web.DispatcherServlet;
import com.github.kelly.web.HandlerMapping;
import com.github.kelly.web.JettyWebServer;
import java.util.Set;

public class SpringApplication {

    public static void run(Class<?> primarySource) {

        // 1. Application.class -> @ComponentScan 서버 뜨기 전에 스캔 먼저 해놓기
        final ComponentScanner componentScanner = new ComponentScanner(primarySource);
        final Set<Class<?>> clazzAnnotatedWith = componentScanner.getClassesAnnotatedWith(Controller.class);
        // 2. @RequestMapping 스캔 후 핸들링 맵핑 해놓기 ->  스캔 완료
        final HandlerMapping handlerMapping = new HandlerMapping(clazzAnnotatedWith);
        final DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMapping);
        // 3. jettyWebServer - Server 에 custom 한 handler 를 등록하고 server 실행.
        final JettyWebServer jettyWebServer = new JettyWebServer(8080, dispatcherServlet);

        try {
            jettyWebServer.start();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


}
