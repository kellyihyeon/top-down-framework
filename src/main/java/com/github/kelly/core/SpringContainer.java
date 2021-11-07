package com.github.kelly.core;

import com.github.kelly.mvc.RequestHandler;
import com.github.kelly.mvc.RequestKey;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * wrapper class
 * Container 를 실행시킨다
 */
public class SpringContainer {

    private SpringContainer() {
        // 인스턴스 생성 막기
    }


    private static class SingletonHolder {
        private static final Container INSTANCE = new Container();
    }


    private static Container getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // 얘를 어떡하냐
    public static Map<RequestKey, RequestHandler> handlerMap() {
        return getInstance().getHandlerMap();
    }


    public static void scan(Class<?> primarySource) {
        try {
            getInstance().getComponentScan(primarySource);

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void port(int port) {
        getInstance().configurePort(port);
    }


    public static void run() {
        getInstance().start();
    }



}
