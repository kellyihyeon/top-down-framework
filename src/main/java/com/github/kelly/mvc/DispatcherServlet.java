package com.github.kelly.mvc;

import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;


public class DispatcherServlet {

    private Map<RequestKey, RequestHandler> handlerMap;
    private final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public DispatcherServlet(Map<RequestKey, RequestHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }


    // run
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {

        final String path = request.getRequestURI();
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());

        final RequestKey requestKey = new RequestKey(path, httpMethod);
        logger.info("requestKey = {}", requestKey);

        if (handlerMap.containsKey(requestKey)) {
            final RequestHandler requestHandler = handlerMap.get(requestKey);

            final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
            final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);

            final MethodExecutor methodExecutor = new MethodExecutor(requestWrapper, responseWrapper);
            final MvcContext context = new MvcContext(methodExecutor);
            requestHandler.handle(context);

        } else {
            if (path.equals("/favicon.ico") && httpMethod == HttpMethod.GET) {
                logger.info("requestKey 가 /favicon? = {}", requestKey);
            } else {
                throw new IllegalArgumentException("정의되지 않은 /path, method 입니다.");
            }
        }

    }

}
