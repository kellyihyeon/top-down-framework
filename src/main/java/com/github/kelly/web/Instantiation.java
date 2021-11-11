package com.github.kelly.web;

import org.reflections.Reflections;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Instantiation {


    public static Object instantiateWithDefaultConstructor(Class<?> clazz) {
        // 접근 지정자와 관계없이 모든 constructors 가져오기
        if (checkInterface(clazz)) {
            System.out.println(clazz + " 은 인터페이스이다.");
            clazz = extractSubClass(clazz);
        }

        final Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            if (declaredConstructor.getParameterCount() == 0) {
                try {
                    return declaredConstructor.newInstance();

                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static boolean checkInterface(Class<?> clazz) {
        return clazz.isInterface();
    }

    private static Class<?> extractSubClass(Class<?> clazz) {
        final Reflections reflections = new Reflections("com.github.kelly.web");
        for (Class<?> subClass : reflections.getSubTypesOf(clazz)) {
            System.out.println("extract sub class from super class = " + subClass);
            return subClass;
        }
        return null;
    }
}
