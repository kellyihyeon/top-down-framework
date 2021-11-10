package com.github.kelly.web;

import com.github.kelly.core.HttpMethod;
import com.github.kelly.web.ui.Model;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.View;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

// central dispatcher
public class DispatcherServlet {


    private final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private final HandlerMapping handlerMapping;


    public DispatcherServlet(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }



    public void doDispatch(HttpServletRequest request, HttpServletResponse response){
        final RequestKey requestKey = extractRequestKeyFromHttpRequest(request);
        final HandlerExecutor handlerExecutor = handlerMapping.getHandlerExecutor(requestKey);

        if (handlerExecutor != null) {
            final Object valueOfReturn = handlerExecutor.invoke(request, response);
            // return 값이 view name 인 경우
            if (valueOfReturn instanceof String) {
                final Path path = Paths.get("/templates", valueOfReturn + ".mustache");
                System.out.println("path = " + path);

                final Object[] methodParameterArgs = handlerExecutor.getMethodParameterArgs();

                Object modelMap = null;
                for (Object object : methodParameterArgs) {
                    System.out.println("object = " + object);
                    if (object instanceof Model) {
                        for (Method method : object.getClass().getMethods()) {
                            if (method.getName().startsWith("getMap")) {
                                final Object instance = Instantiation.instantiateWithDefaultConstructor(object.getClass());
                                try {
                                    modelMap = method.invoke(instance);
                                    System.out.println("modelMap = " + modelMap);

                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                } //for
                final ViewCompiler viewCompiler = new ViewCompiler(path, modelMap);
                final String renderedTemplate = viewCompiler.compile();
                response.setContentType("text/html");

                try {
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(renderedTemplate.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                // return 값이 객체 일 때
                System.out.println("controller - method.invoke() = return: Object");
            }


        } else {
            // exception handling facilities.
            logger.info("{} 와 연결된 HandlerExecutor 가 없습니다.", requestKey);
        }
    }

    private RequestKey extractRequestKeyFromHttpRequest(HttpServletRequest request) {
        final String uri = request.getRequestURI();
        final HttpMethod method = HttpMethod.valueOf(request.getMethod());
        return new RequestKey(uri, method);
    }
}
