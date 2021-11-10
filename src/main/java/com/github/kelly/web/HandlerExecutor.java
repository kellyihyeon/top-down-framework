package com.github.kelly.web;

import org.reflections.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

public class HandlerExecutor {

    private final Object instance;
    private final Method method;

    private Object valueOfReturn = null;
    private Object[] methodParameterArgs;


    public HandlerExecutor(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        this.methodParameterArgs = new Object[method.getParameterCount()];
    }


    public Object invoke(Object... args) {   // parameters for method
        try {
            // return 타입이 없는 경우 -> controller 에서 바로 response 내려주기
            if (method.getGenericReturnType().equals(void.class)) {
                valueOfReturn = method.invoke(instance, method.getParameterCount() == 0 ? null : args);
                System.out.println("return 타입이 없는 method 반환값 = " + valueOfReturn); // null
            }
            else {
                // 1. 파라미터가 없는 경우 하지만 return 타입은 있는 경우
                if (method.getParameters().length == 0) {
                    // ModelAndView 객체 반환
                    valueOfReturn = method.invoke(instance);
                    System.out.println("파라미터가 0개인 메소드의 반환 값 = " + valueOfReturn);
                }
                // 2. 파라미터가 1개 이상이고, return 타입이 있는 경우
                else {
                    valueOfReturn = invokeMethodWithParametersAndReturnValue(method);
                }
            }
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return valueOfReturn;
    }


    private Object invokeMethodWithParametersAndReturnValue(Method method) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Object instanceOfClass = null;

        // 파라미터 -> 인스턴스화 시키기
        for (Parameter parameter : method.getParameters()) {
            final String classType = parameter.getType().toString().split(" ")[0];
            final String className = parameter.getType().toString().split(" ")[1];

            final Class<?> classWithGivenName = Class.forName(className);


            if (classType.equals("interface")) {
                final Reflections reflections = new Reflections("com.github.kelly.web");
                final Set<Class<?>> subTypesOf = reflections.getSubTypesOf((Class<Object>) classWithGivenName);
                for (Class<?> subClass : subTypesOf) {
                    System.out.println("subClass = " + subClass);
                    instanceOfClass = Instantiation.instantiateWithDefaultConstructor(subClass);
                }
            } else {
                instanceOfClass = Instantiation.instantiateWithDefaultConstructor(classWithGivenName);
            }

            // 파라미터 args 에 인스턴스 넣기
            for (int i = 0; i < method.getParameterCount(); i++) {
                methodParameterArgs[i] = instanceOfClass;
                System.out.println("메소드 파라미터 args - objects[i] = " + methodParameterArgs[i]);
            }

            setMethodParameterArgs(methodParameterArgs);
            // view name 반환
            valueOfReturn = method.invoke(instance, methodParameterArgs);
            System.out.println("파라미터가 여러개인 메소드의 반환 값 = " + valueOfReturn);
        }   // for


        return valueOfReturn;
    }

    private void setMethodParameterArgs(Object[] objects) {
        this.methodParameterArgs = objects;
    }

    public Object[] getMethodParameterArgs() {
        return methodParameterArgs;
    }
}
