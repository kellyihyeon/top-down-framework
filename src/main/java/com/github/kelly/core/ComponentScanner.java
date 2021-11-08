package com.github.kelly.core;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.annotation.Annotation;
import java.util.*;

// primarySource 의 basePackage 내의 @ComponentScan 스캔하기
public class ComponentScanner {

    private final String basePackage;
    private final Reflections reflections;
    private final Logger log = LoggerFactory.getLogger(ComponentScanner.class);


    public ComponentScanner(Class<?> primarySource) {
        ComponentScan componentScan = primarySource.getDeclaredAnnotation(ComponentScan.class);
        if ("".equals(componentScan.basePackage())) {
            this.basePackage = primarySource.getPackageName();  // Application 의 package
        } else {
            this.basePackage = componentScan.basePackage();
        }

        this.reflections = new Reflections(basePackage);
    }


    // @Controller, @Service, @Repository
    public Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> targetAnnotation) {
        final Set<Class<?>> classesAnnotatedWith = reflections.getTypesAnnotatedWith(targetAnnotation);
        for (Class<?> classType : classesAnnotatedWith) {
            log.info("class = {}, classType = {}", this.getClass(), classType);
        }
        return classesAnnotatedWith;
    }
}
