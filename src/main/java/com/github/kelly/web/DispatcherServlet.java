package com.github.kelly.web;

import com.github.kelly.core.HttpMethod;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet {


    private final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private final HandlerMapping handlerMapping;


    public DispatcherServlet(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }



    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        final RequestKey requestKey = extractRequestKeyFromHttpRequest(request);
        final HandlerExecutor handlerExecutor = handlerMapping.getHandlerExecutor(requestKey);
        if (handlerExecutor != null) {
            handlerExecutor.invoke(request, response);
        } else {
            logger.info("{} 와 연결된 HandlerExecutor 가 없습니다.", requestKey);
        }
    }


    private RequestKey extractRequestKeyFromHttpRequest(HttpServletRequest request) {
        final String uri = request.getRequestURI();
        final HttpMethod method = HttpMethod.valueOf(request.getMethod());
        return new RequestKey(uri, method);
    }
}
