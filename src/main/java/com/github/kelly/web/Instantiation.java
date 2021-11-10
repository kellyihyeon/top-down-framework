package com.github.kelly.web;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Instantiation {


    public static Object instantiateWithDefaultConstructor(Class<?> clazz) {
        // 접근 지정자와 관계없이 모든 constructors 가져오기
        final Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            if (declaredConstructor.getParameterCount() == 0) {
                try {
                    return declaredConstructor.newInstance();

                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }//for
        return null;
    }
}
