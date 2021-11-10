package com.github.kelly.web;

import com.github.kelly.core.HttpMethod;
import com.github.kelly.web.ui.Model;
import com.github.kelly.web.ui.ModelAndView;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
                System.out.println("valueOfReturn 이 String 인 경우 = " + valueOfReturn);
                Object modelMap = null;
                final Object[] methodParameterArgs = handlerExecutor.getMethodParameterArgs();

                for (Object object : methodParameterArgs) {
                    System.out.println("object = " + object);
                    if (object instanceof Model) {  // ModelImpl
                        for (Method method : object.getClass().getMethods()) {
                            if (method.getName().startsWith("getMap")) {
                                try {
                                    modelMap = method.invoke(object);
                                    System.out.println("modelMap = " + modelMap);

                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                } //for
                final ViewCompiler viewCompiler = new ViewCompiler(valueOfReturn, modelMap);
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
                System.out.println("valueOfReturn 이 객체인 경우 = " + valueOfReturn);
                if (valueOfReturn != null) {
                    if (valueOfReturn instanceof ModelAndView) {
                        final String viewName = ((ModelAndView) valueOfReturn).getViewName();

                        final Map<String, Object> modelMap = ((ModelAndView) valueOfReturn).getModelMap();
                        final ViewCompiler compiler = new ViewCompiler(viewName, modelMap);
                        final String renderedTemplate = compiler.compile();
                        response.setContentType("text/html");

                        try {
                            ServletOutputStream outputStream = response.getOutputStream();
                            outputStream.write(renderedTemplate.getBytes(StandardCharsets.UTF_8));
                            outputStream.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
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
