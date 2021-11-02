package com.github.kelly.mvc;

import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MvcResponse implements Response{

    private final HttpServletResponse responseWrapper;

    public MvcResponse(HttpServletResponse responseWrapper) {
        this.responseWrapper = responseWrapper;
    }


    @Override
    public void execute(Method method, Object obj) {

        try {
            method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
