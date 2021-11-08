package com.github.kelly.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecutor {

    private final Object instance;
    private final Method method;

    public HandlerExecutor(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public void invoke(Object ... args) {   // parameters for method
        try {
            method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
