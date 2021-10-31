package com.github.kelly.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;


public class DispatcherServlet {

//    Map<RequestKey, >

    // path, method 를 얻어서 '사용자 정의 컨트롤러' 에 정의된 게 있는지 확인한 다음에
    // 있으면 거기로 연결해준다.
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        final String path = request.getRequestURI();    // /hello
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());  // get
        // request 에서 빼낸 path, httpMethod 조합으로 key 를 만든다
        final RequestKey requestKey = new RequestKey(path, httpMethod);

        // path, method 한 세트로 맞는 게 있는지 '컨트롤러' 목록에서 살펴본다




    }
}
