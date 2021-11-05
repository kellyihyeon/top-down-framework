package com.github.kelly.mvc;

import com.github.kelly.core.ComponentScanner;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DispatcherServlet {

    private Map<RequestKey, RequestHandler> handlerMap; //
    private final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<Resolver> resolvers = new ArrayList<>();


    public DispatcherServlet(Map<RequestKey, RequestHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public DispatcherServlet() {
        resolvers.add(new StaticFileResolver());
        resolvers.add(new UserDefineResolver());
    }

    // handler -> dispatcher.doDispatch();
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        // 1. /hello 를 요청한 경우
        // 2. /hello.html 파일을 요청한 경우
        // 어느 경우든 메소드를 실행하는 결과를 만든다.

        final String path = request.getRequestURI();
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());

        final RequestKey requestKey = new RequestKey(path, httpMethod);
        logger.info("requestKey = {}", requestKey);

        // /hello 요청 <- 컴포넌트 스캔의 대상
        // 필요한 변수: request, response
        if (handlerMap.containsKey(requestKey)) {
            final RequestHandler requestHandler = handlerMap.get(requestKey);

            final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
            final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);

            final MethodExecutor methodExecutor = new MethodExecutor(requestWrapper, responseWrapper);
            final MvcContext context = new MvcContext(methodExecutor);
            requestHandler.handle(context);

            // /hello.html 요청 <- 컴포넌트 스캔의 대상이 아닌 것
        } else {
            // staticController 의 execute 메소드 직접 실행
            if (path.equals("/favicon.ico") && httpMethod == HttpMethod.GET) {
                logger.info("requestKey 가 /favicon? = {}", requestKey);
            } else {
                throw new IllegalArgumentException("정의되지 않은 /path, method 입니다.");
            }
        }


    }

    public RequestHandler doMapping(HttpServletRequest request, HttpServletResponse response) {
        for (Resolver resolver : resolvers) {
            // happy path
            return resolver.dispatch(request, response);
        }
        return context -> context.response().error();   // temporary method
    }
}
