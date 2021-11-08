//package com.github.kelly.web;
//
//import com.github.kelly.app.controller.StaticFileController;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.lang.reflect.Method;
//
//public class StaticFileResolver implements Resolver{
//
//
//    private final StaticFileController staticFileController = new StaticFileController();   //
//    private final Logger logger = LoggerFactory.getLogger(StaticFileResolver.class);
//
//
//
//    @Override
//    public RequestHandler dispatch(HttpServletRequest request, HttpServletResponse response) {
//        RequestHandler requestHandler = null;
//        final Class<?> thisClass = staticFileController.getClass();
//
//        for (Method method : staticFileController.getClass().getMethods()) {
//            if (method.getName().equals("run")) {
//                logger.info("StaticFileResolver.class - staticFileController 의 method.getName() = {}", method.getName());
//                requestHandler =
//                        context -> context.response().execute(method, thisClass);
//            }
//        }
//        System.out.println("requestHandler = " + requestHandler);
//
//        return requestHandler;
//    }
//}
