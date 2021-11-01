package com.github.kelly.mvc;

import com.github.kelly.core.SpringContainer;

// Spring Framework
public class SpringApplication {    // spring container ?

    /**
     * 1. 스캔
     * 2. 등록
     * 3. 서버 실행
     *
     * SpringApplication.run(Application.class);
     * @param primarySource
     */
    public static void run(Class<?> primarySource) {

        // 스캔
        SpringContainer.scan(primarySource);

        SpringContainer.get();


    }
}
