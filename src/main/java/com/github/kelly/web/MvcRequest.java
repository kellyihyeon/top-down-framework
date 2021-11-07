package com.github.kelly.web;

import jakarta.servlet.http.HttpServletRequestWrapper;

public class MvcRequest implements Request {

    private final HttpServletRequestWrapper requestWrapper;

    public MvcRequest(HttpServletRequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    @Override
    public void deliver(Object obj) {
        // add later~~
        final String id = requestWrapper.getParameter("id");
        final String password = requestWrapper.getParameter("password");
        System.out.println("id = " + id);
        System.out.println("password = " + password);
    }
}
