package com.github.kelly.mvc;

import com.github.kelly.core.ComponentScanner;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
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

    public static void deliverToView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // uri = /hello, method = GET
        final String uriWithoutSlash = request.getRequestURI().replace("/", "");
        System.out.println("uriWithoutSlash = " + uriWithoutSlash);

        final List<String> staticFileList = ComponentScanner.staticFileList;
        //staticFileList = [hello.html, login.html, signup.html, test.html]
        for (String fileFullName : staticFileList) {
            final String[] stringsBySplit = fileFullName.split("\\.");
            final String fileName = stringsBySplit[0];
            System.out.println("fileName = " + fileName);

            if (uriWithoutSlash.equals(fileName)) {
//                String path = "/src/main/resources/static/" + fileFullName;
                response.setStatus(302);
                // Location: http://localhost:12345/src/main/resources/static/hello.html
                response.setHeader("Location", fileFullName);
//                response.sendRedirect("/src/main/resources/static/" + fileFullName);
                // 아......... request 로 반복되네 ...
            }
        }


    }


}
