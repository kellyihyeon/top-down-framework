package com.github.kelly.mvc;

import java.lang.reflect.Method;

public interface Response {

    void execute(Method method, Class<?> obj);
}
