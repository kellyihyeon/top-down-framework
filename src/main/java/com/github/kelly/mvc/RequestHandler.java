package com.github.kelly.mvc;

@FunctionalInterface
public interface RequestHandler {

    void handle(Context context);
}
