package com.github.kelly.mvc;

import jakarta.servlet.http.*;
import java.util.Map;


public class DispatcherServlet {

    private Map<RequestKey, RequestHandler> handlerMap;

    public DispatcherServlet(Map<RequestKey, RequestHandler> handlerMap) {
        this.handlerMap = handlerMap;
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
        System.out.println("handlerMap.containsKey(requestKey) = " + handlerMap.containsKey(requestKey));

        // path, method 한 세트로 맞는 게 있는지 '컨트롤러' 목록에서 살펴본다
        // handlerMap 에서 get 할 차례
        if (handlerMap.containsKey(requestKey)) {
            final RequestHandler requestHandler = handlerMap.get(requestKey);

            final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
            final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);

            final MvcRequest mvcRequest = new MvcRequest(requestWrapper);
            final MvcResponse mvcResponse = new MvcResponse(requestWrapper, responseWrapper);

            final MvcContext context = new MvcContext(mvcResponse);
//            final MvcContext allContext = new MvcContext(mvcRequest, mvcResponse);

            requestHandler.handle(context); // handle 메소드 실행 -> method.invoke(instance);

        } else {
            throw new IllegalArgumentException("일치하는 path and method 가 없다.");
        }

    }

}
