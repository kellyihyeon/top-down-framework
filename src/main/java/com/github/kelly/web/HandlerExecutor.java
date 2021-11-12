package com.github.kelly.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.*;

public class HandlerExecutor {

    private final Object instance;
    private final Method method;

    private Object[] methodParameterArgs;   // refactoring - 고민


    public HandlerExecutor(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        this.methodParameterArgs = new Object[method.getParameterCount()];
    }


    /**
     * invoke the method of controller
     * @param args - jetty Request, jetty Response
     * @return {@code Object} is value of return. It might be null or Object(String view name, Model, ModelAndView, etc ...)
     */
    public Object invoke(Object... args) {
        Object returnValue = null;
        try {
            if (method.getParameterCount() == 0) {
                returnValue = method.invoke(instance);
            } else if (areMethodParametersSameAsArgs(method)) {
                returnValue = method.invoke(instance, args);
            } else {
                final Object[] parameterArgs = createMethodParameterArgs(method);
                returnValue = method.invoke(instance, parameterArgs);
            }

        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    private boolean areMethodParametersSameAsArgs(Method method) {   // request, response
        final Parameter[] methodParameters = method.getParameters();
        if (methodParameters[0].getType().getName().equals(HttpServletRequest.class.getName())) {
            System.out.println("첫 번째 메소드 파라미터 = HttpServletRequest");
            return methodParameters[1].getType().getName().equals(HttpServletResponse.class.getName());
        }
        System.out.println("첫 번째 메소드 파라미터 != HttpServletRequest");
        return false;
    }


    /**
     * 파라미터 -> 인스턴스화 시키기 -> 파라미터 args 에 인스턴스 넣기
     *
     * @param method
     * @return {@code Object[] methodParameterArgs} methodParametersArgs is same as method parameter of controller.
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object[] createMethodParameterArgs(Method method) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < method.getParameterCount(); i++) {
            final Parameter[] methodParameters = method.getParameters();
            final Class<?> classFromParameter = methodParameters[i].getType();
            Object instanceOfClass = Instantiation.instantiateWithDefaultConstructor(classFromParameter);

            methodParameterArgs[i] = instanceOfClass;
            System.out.println("메소드 파라미터 args - objects[i] = " + methodParameterArgs[i]);
            setMethodParameterArgs(methodParameterArgs);
        }
        return methodParameterArgs;
    }

    private void setMethodParameterArgs(Object[] objects) {
        this.methodParameterArgs = objects;
    }

    public Object[] getMethodParameterArgs() {
        return methodParameterArgs;
    }
}
