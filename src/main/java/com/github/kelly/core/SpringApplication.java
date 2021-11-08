package com.github.kelly.core;


public class SpringApplication {

    public static void run(Class<?> primarySource) {

        // 1. Application.class -> @ComponentScan 서버 뜨기 전에 스캔 먼저 해놓기
        final ComponentScanner componentScanner = new ComponentScanner(primarySource);
        componentScanner.getClassesAnnotatedWith(Controller.class);
        // 2. @RequestMapping 스캔 후 핸들링 맵핑 해놓기


//
//        SpringContainer.scan(primarySource);
//        SpringContainer.port(12345);
//        SpringContainer.run();


    }
}
