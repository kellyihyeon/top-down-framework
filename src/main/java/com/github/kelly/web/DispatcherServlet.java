package com.github.kelly.web;


import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet {


    private final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);


    public DispatcherServlet() {
    }




    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        RequestHandler requestHandler = null;
        // handler 1 = staticFile 처리 담당
        if (request.getRequestURI().contains(".")) {
            logger.info("request uri = {}", request.getRequestURI());
            final StaticFileResolver staticFileResolver = new StaticFileResolver();
            requestHandler = staticFileResolver.dispatch(request, response);
        }
        // handler 2 = userDefine 처리 담당
        else {
            logger.info("request uri = {}", request.getRequestURI());
            final UserDefineResolver userDefineResolver = new UserDefineResolver();
            requestHandler = userDefineResolver.dispatch(request, response);
        }

        // handler 실행하는 담당
        // >> 응답 주는 class 부터 쓸 것만 남기고 리팩토링 할 것
        final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
        final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);

        final MethodExecutor methodExecutor = new MethodExecutor(requestWrapper, responseWrapper);
        final MvcContext context = new MvcContext(methodExecutor);
        if (requestHandler != null) {
            requestHandler.handle(context);
        }
    }
}
