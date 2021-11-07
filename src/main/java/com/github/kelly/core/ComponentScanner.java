package com.github.kelly.core;

import com.github.kelly.mvc.RequestHandler;
import com.github.kelly.mvc.RequestKey;
import com.github.kelly.mvc.RequestMapping;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

// base package 기준 모든 어노테이션 component scan 하기
public class ComponentScanner {

    private final ComponentScan componentScan;
    private Reflections reflections;
    public static List<String> staticFileList = new ArrayList<>();

    private static final Map<String, Set<Class<?>>> scannerMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(ComponentScanner.class);


    public ComponentScanner(Class<?> primarySource) {
        this.componentScan = primarySource.getAnnotation(ComponentScan.class);
        this.reflections = new Reflections(componentScan.basePackage());    // app
    }

    public void scan() {
        controllerScan();
    }


    // 사용자 정의 컨트롤러 스캔
    private void controllerScan() {
        final Set<Class<?>> controllerScannedClazz = reflections.getTypesAnnotatedWith(Controller.class);
        scannerMap.put("controller", controllerScannedClazz);
    }


    // 책임이 어디에?
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
}
