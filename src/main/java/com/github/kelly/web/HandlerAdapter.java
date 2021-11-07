package com.github.kelly.web;

import java.util.Map;

// handlerMap ->  key( /hello, get ), requestHandler( sayHello(); )
// private Map<RequestKey, RequestHandler> handlerMap;

// if (handlerMap.containsKey(requestKey)) -> requestHandler 실행
// else -> not found 컨트롤러의 메소드 실행
// staticFileController 는? (예: /hello.html, get -> hello.html 파일 response)
// staticFileController 의 메소드 실행
public class HandlerAdapter {

    private final RequestKey requestKey;
    private final Map<RequestKey, RequestHandler> handlerMap;

    public HandlerAdapter(RequestKey requestKey, Map<RequestKey, RequestHandler> handlerMap) {
        this.requestKey = requestKey;
        this.handlerMap = handlerMap;
    }



}
