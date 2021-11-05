package com.github.kelly.mvc;

import com.github.kelly.core.SpringContainer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


// app 에서 정의한 controller requestMapping -> /hello
public class UserDefineResolver implements Resolver {


    private final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);


    @Override
    public RequestHandler dispatch(HttpServletRequest request, HttpServletResponse response) {
        // ? field 에서는 왜 접근을 못할까
        final Map<RequestKey, RequestHandler> handlerMap = SpringContainer.handlerMap();

        final String path = request.getRequestURI();
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());

        final RequestKey requestKey = new RequestKey(path, httpMethod);
        logger.info("requestKey = {}", requestKey);

        // /hello, get <- 컴포넌트 스캔의 대상
        RequestHandler requestHandler = null;
        if (handlerMap.containsKey(requestKey)) {
            requestHandler = handlerMap.get(requestKey);

//            final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
//            final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
//
//            final MethodExecutor methodExecutor = new MethodExecutor(requestWrapper, responseWrapper);
//            final MvcContext context = new MvcContext(methodExecutor);
//            requestHandler.handle(context);
        }
        return requestHandler;
    }
}

