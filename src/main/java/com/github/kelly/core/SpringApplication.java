package com.github.kelly.core;

/**
 * 1. 스캔
 * 2. 등록
 * 3. 서버 실행
 */
public class SpringApplication {

    public static void run(Class<?> primarySource) {

        SpringContainer.scan(primarySource);

        SpringContainer.port(12345);

        SpringContainer.run();


    }
}
