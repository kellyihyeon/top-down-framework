package com.github.kelly.mvc;

import com.github.kelly.core.SpringContainer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;


// app 에서 정의한 controller requestMapping -> /hello
public class UserDefineResolver implements Resolver {


    private final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);



    @Override
    public RequestHandler dispatch(HttpServletRequest request, HttpServletResponse response) {
        final Map<RequestKey, RequestHandler> handlerMap = SpringContainer.handlerMap();

        final String path = request.getRequestURI();
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());

        final RequestKey requestKey = new RequestKey(path, httpMethod);
        logger.info("requestKey = {}", requestKey);

        // /hello, get <- 컴포넌트 스캔의 대상
        RequestHandler requestHandler = null;
        if (handlerMap.containsKey(requestKey)) {
            requestHandler = handlerMap.get(requestKey);
        }
        return requestHandler;
    }
}

