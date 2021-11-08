package com.github.kelly.core;

import com.github.kelly.web.RequestHandler;
import com.github.kelly.web.RequestKey;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

// primarySource 의 basePackage 내의 @ComponentScan 스캔하기
public class ComponentScanner {

    private final Reflections reflections;

    private static final Map<String, Set<Class<?>>> scannerMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(ComponentScanner.class);


    public ComponentScanner(Class<?> primarySource) {
        ComponentScan componentScan = primarySource.getDeclaredAnnotation(ComponentScan.class);

        this.reflections = new Reflections(componentScan.basePackage());
    }

    public void scan() {
        controllerScan();
    }



    // 사용자 정의 컨트롤러 스캔
    private void controllerScan() {
        final Set<Class<?>> controllerScannedClazz = reflections.getTypesAnnotatedWith(Controller.class);
        scannerMap.put("controller", controllerScannedClazz);
    }


    // 책임이 어디에?     >> Handler Mapping
    public void addKeyAndHandlerToMap(Map<RequestKey, RequestHandler> handlerMap) {
        for (Class<?> controllerClass : scannerMap.get("controller")) {
            logger.info("controllerClass.getName() = {}", controllerClass.getName());

            for (Method method : controllerClass.getMethods()) {
                // @RequestMapping 정보 뽑기
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    final RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    final RequestKey requestKey = new RequestKey(requestMapping.value(), requestMapping.method());

                    RequestHandler requestHandler =
                            // void handle(Context context);
                            context -> context.response().execute(method, controllerClass);
                    handlerMap.put(requestKey, requestHandler);
                }
            }

        } // for
    }

    // @Controller, @Service, @Repository
    public Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> targetAnnotation) {
        final Set<Class<?>> classesAnnotatedWith = reflections.getTypesAnnotatedWith(targetAnnotation);
        for (Class<?> aClassType : classesAnnotatedWith) {
            System.out.println("aClassType = " + aClassType);
        }
        return classesAnnotatedWith;
    }
}
