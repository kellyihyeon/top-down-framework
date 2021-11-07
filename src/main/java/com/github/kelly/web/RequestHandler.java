package com.github.kelly.web;

@FunctionalInterface
public interface RequestHandler {

    void handle(Context context);
}
