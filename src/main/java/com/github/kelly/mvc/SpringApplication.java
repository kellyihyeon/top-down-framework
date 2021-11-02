package com.github.kelly.mvc;

import com.github.kelly.core.SpringContainer;
import jakarta.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

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

        // 등록
//        SpringContainer.register();

        SpringContainer.run();


    }
}
