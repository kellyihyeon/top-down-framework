package com.github.kelly.mvc;

import com.github.kelly.app.StaticFileController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class StaticFileResolver implements Resolver{

    private final String STATIC_FILE_PATH = "static";
    private static final StaticFileController staticFileController = new StaticFileController();   //
    private final Logger logger = LoggerFactory.getLogger(StaticFileResolver.class);

    public boolean supports() {
        //
        return true;
    }


    @Override
    public RequestHandler dispatch(HttpServletRequest request, HttpServletResponse response) {
        RequestHandler requestHandler = null;
        for (Method method : staticFileController.getClass().getMethods()) {
            if (method.getName().equals("run")) {
                logger.info("StaticFileResolver.class - staticFileController 의 method.getName() = {}", method.getName());
                requestHandler =
                        context -> context.response().execute(method, staticFileController.getClass());
            }
        }
        return requestHandler;
    }
}
