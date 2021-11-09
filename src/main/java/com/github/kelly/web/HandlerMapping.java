package com.github.kelly.web;

import com.github.kelly.core.HttpMethod;
import com.github.kelly.core.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// This class can not be extended.
public final class HandlerMapping {

    private final Map<RequestKey, HandlerExecutor> handlerMapping = new HashMap<>();  // 아무데서나 쓸 수 없게
    private final Logger log = LoggerFactory.getLogger(HandlerMapping.class);



    public HandlerMapping(Set<Class<?>> classes) {
        Object instance = null;

        // 기본 생성자를 이용해서 instance 생성
        for (Class<?> controllerClass : classes) {
            final Constructor<?>[] declaredConstructors = controllerClass.getDeclaredConstructors();
            for (Constructor<?> constructor : declaredConstructors) {
                if (constructor.getParameterCount() == 0) { // 기본 생성자
                    try {
                        instance = constructor.newInstance(null);
                        log.info("controller instance 생성 = {}", instance);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }  // if
            }

            for (Method method : controllerClass.getMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    final RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                    final String path = requestMapping.value();
                    final HttpMethod httpMethod = requestMapping.method();
                    final RequestKey requestKey = new RequestKey(path, httpMethod);
                    log.info(requestKey.toString());

                    final HandlerExecutor handlerExecutor = new HandlerExecutor(instance, method);


                    handlerMapping.put(requestKey, handlerExecutor);
                }
            }
        }
    }

    public HandlerExecutor getHandlerExecutor(RequestKey requestKey) {
        return handlerMapping.get(requestKey);
    }
}
