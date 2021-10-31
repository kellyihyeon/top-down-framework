package com.github.kelly.mvc;

import com.github.kelly.core.ComponentScan;
import com.github.kelly.core.Controller;
import org.reflections.Reflections;
import java.util.Set;

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
        // 1. 컴포넌트 스캔 어노테이션을 찾자
        final ComponentScan componentScan = primarySource.getAnnotation(ComponentScan.class);
        System.out.println("componentScan.basePackage() = " + componentScan.basePackage());
        final Reflections reflections = new Reflections(componentScan.basePackage());
        final Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> controllerScannedClass : classSet) {
            System.out.println("controllerScannedClass.getName() = " + controllerScannedClass.getName());
        }

        // 2. 컨트롤러를 등록하자


    }
}
