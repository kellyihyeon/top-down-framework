package com.github.kelly.mvc;

import jakarta.servlet.http.*;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Map;


public class DispatcherServlet {

    private Map<RequestKey, RequestHandler> handlerMap2;

    public DispatcherServlet(Map<RequestKey, RequestHandler> handlerMap2) {
        this.handlerMap2 = handlerMap2;
    }


    // path, method 를 얻어서 '사용자 정의 컨트롤러' 에 정의된 게 있는지 확인한 다음에
    // 있으면 거기로 연결해준다.
    public void doDispatch(
            HttpServletRequest request,
            HttpServletResponse response)
    {
        final String path = request.getRequestURI();    // /hello
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());  // get
        final RequestKey requestKey = new RequestKey(path, httpMethod);
        System.out.println("requestKey = " + requestKey);
        System.out.println("handlerMap2.containsKey(requestKey) = " + handlerMap2.containsKey(requestKey));

        // path, method 한 세트로 맞는 게 있는지 '컨트롤러' 목록에서 살펴본다
        // handlerMap 에서 get 할 차례
        if (handlerMap2.containsKey(requestKey)) {
            final RequestHandler requestHandler = handlerMap2.get(requestKey);

            final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
            final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);

            final MvcRequest mvcRequest = new MvcRequest(requestWrapper);
            final MvcResponse mvcResponse = new MvcResponse(responseWrapper);
            final MvcContext context = new MvcContext(mvcResponse);
            final MvcContext allContext = new MvcContext(mvcRequest, mvcResponse);

            requestHandler.handle(allContext); // handle 메소드 실행 -> method.invoke(instance);

        } else {
            throw new IllegalArgumentException("일치하는 path and method 가 없다.");
        }



    }
}
