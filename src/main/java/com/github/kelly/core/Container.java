package com.github.kelly.core;

import com.github.kelly.mvc.HttpMethod;
import com.github.kelly.mvc.RequestHandler;
import com.github.kelly.mvc.RequestKey;
import com.github.kelly.mvc.RequestMapping;
import org.eclipse.jetty.http.HttpParser;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;

public class Container {

    private final Map<String, Set<Class<?>>> containerMap = new HashMap<>();
    private final Map<RequestKey, RequestHandler> handlerMap = new HashMap<>();


    public void getComponentScan(Class<?> primarySource) {

        // primarySource = Application.class
        final ComponentScan componentScan = primarySource.getAnnotation(ComponentScan.class);
        // "com.github.kelly.app"
        final Reflections reflections = new Reflections(componentScan.basePackage());

        // @Controller 뽑기
        final Set<Class<?>> controllerScannedClazz = reflections.getTypesAnnotatedWith(Controller.class);
        containerMap.put("controller", controllerScannedClazz);
        System.out.println("containerMap.get(\"controller\") = " + containerMap.get("controller"));

        final Set<Class<?>> controllerBeans = containerMap.get("controller");
        for (Class<?> controllerBean : controllerBeans) {
            System.out.println("controllerBean.getName() = " + controllerBean.getName());
            final Method[] methods = controllerBean.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    System.out.println("method.getName() = " + method.getName());
                    final RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    System.out.println("requestMapping.value() = " + requestMapping.value());
                    System.out.println("requestMapping.method() = " + requestMapping.method());
                }
                
            }
//            final RequestMapping requestMapping = controllerBean.getAnnotation(RequestMapping.class);
//            // @RequestMapping(value = "", method = GET) -> 정의 해놓은 걸 Map 에 등록하기
//            final String path = requestMapping.value();
//            final HttpMethod method = requestMapping.method();
//            final RequestKey requestKey = new RequestKey(path, method);
//            // requestHandler =
//            // TestController 에서 @RequestMapping 달려있는 메소드를 구해야 한다.
//            final Method[] methods = controllerBean.getMethods();
//            for (Method method : methods) {
//                method
//            }

//            handlerMap.put(requestKey, )

        } //

    }
}
