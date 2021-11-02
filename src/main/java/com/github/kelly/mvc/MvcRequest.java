package com.github.kelly.mvc;

import jakarta.servlet.http.HttpServletRequestWrapper;

public class MvcRequest implements Request {

    private final HttpServletRequestWrapper requestWrapper;

    public MvcRequest(HttpServletRequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    @Override
    public void deliver(Object obj) {
        // add later~~
    }
}
