package com.github.kelly.core;

import com.github.kelly.web.HandlerMapping;
import java.util.Set;

public class SpringApplication {

    public static void run(Class<?> primarySource) {

        // 1. Application.class -> @ComponentScan 서버 뜨기 전에 스캔 먼저 해놓기
        final ComponentScanner componentScanner = new ComponentScanner(primarySource);
        final Set<Class<?>> clazzAnnotatedWith = componentScanner.getClassesAnnotatedWith(Controller.class);
        // 2. @RequestMapping 스캔 후 핸들링 맵핑 해놓기
        final HandlerMapping handlerMapping = new HandlerMapping(clazzAnnotatedWith);



//        SpringContainer.scan(primarySource);
//        SpringContainer.port(12345);
//        SpringContainer.run();

    }


}
