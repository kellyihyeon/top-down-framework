package com.github.kelly.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodExecutor implements Response {

    private final HttpServletRequest requestWrapper;
    private final HttpServletResponse responseWrapper;


    // controller 에 전달
    public MethodExecutor(HttpServletRequest requestWrapper, HttpServletResponse responseWrapper) {
        this.requestWrapper = requestWrapper;
        this.responseWrapper = responseWrapper;
    }


    @Override
    public void execute(Method method, Class<?> obj) {
        try {
            final Object newInstance = obj.getDeclaredConstructor().newInstance();
            final Object[] parametersArray = {requestWrapper, responseWrapper};

            if (method.getParameterCount() == 0) {
                method.invoke(newInstance);
            } else {
                method.invoke(newInstance, parametersArray);
            }

        } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

}
