package com.github.kelly.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MvcResponse implements Response {

    private final HttpServletRequest requestWrapper;
    private final HttpServletResponse responseWrapper;

    public MvcResponse(HttpServletRequest requestWrapper, HttpServletResponse responseWrapper) {
        this.requestWrapper = requestWrapper;
        this.responseWrapper = responseWrapper;
    }

    //    public MvcResponse(HttpServletResponse responseWrapper) {
//        this.responseWrapper = responseWrapper;
//    }

    @Override
    public void execute(Method method, Class<?> obj) {
        try {
            System.out.println("method.getParameterCount() = " + method.getParameterCount());
            if (method.getParameterCount() == 0) {
                System.out.println("method.getName() = " + method.getName());
                System.out.println("obj.getName() = " + obj.getName());
                method.invoke(obj.getDeclaredConstructor().newInstance());
            } else if (method.getParameterCount() == 1) {
                final Object newInstance = obj.getConstructor().newInstance();
                final Object[] parametersArray = {responseWrapper};
                method.invoke(newInstance, parametersArray);    // parameter 가 response 인 경우
            } else {
                final Object newInstance = obj.getConstructor().newInstance();
                final Object[] parametersArray = {requestWrapper, responseWrapper};
                method.invoke(newInstance, parametersArray);
            }

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

}
