package com.github.kelly.mvc;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Map;


public class DispatcherServlet extends HttpServlet {

    private final Map<RequestKey, RequestHandler> handlerMap2;

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
        // request 에서 빼낸 path, httpMethod 조합으로 key 를 만든다
        final RequestKey requestKey = new RequestKey(path, httpMethod);
        System.out.println("requestKey = " + requestKey);

        // path, method 한 세트로 맞는 게 있는지 '컨트롤러' 목록에서 살펴본다
        // handlerMap 에서 get 할 차례
        if (handlerMap2.containsKey(requestKey)) {
            final RequestHandler requestHandler = handlerMap2.get(requestKey);
            final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
            final MvcResponse mvcResponse = new MvcResponse(responseWrapper);
            final MvcContext context = new MvcContext(mvcResponse);

            requestHandler.handle(context); // handle 메소드 실행 -> method.invoke(instance);

            // public Request(HttpChannel channel, HttpInput input)


        } else {
            throw new IllegalArgumentException("일치하는 path and method 가 없다.");
        }



    }
}
