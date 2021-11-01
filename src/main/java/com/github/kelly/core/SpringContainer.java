package com.github.kelly.core;

/**
 * wrapper class
 * Container 를 실행시킨다
 */
public class SpringContainer {

    private SpringContainer() {
    }

    private static class SingletonHolder {
        private static final Container INSTANCE = new Container();
    }

    private static Container getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // 우선 여기에다가 코드 정리 다 해놓음
    public static void scan(Class<?> primarySource) {
        getInstance().getComponentScan(primarySource);

    }

    public static void get() {
//        getInstance().addHandler();

    }


}
