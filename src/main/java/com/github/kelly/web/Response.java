package com.github.kelly.web;

import java.lang.reflect.Method;

public interface Response {

    // method invoke 를 하기 위함
    void execute(Method method, Class<?> obj);

    void error();
}
