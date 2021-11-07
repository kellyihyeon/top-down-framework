package com.github.kelly.web;

public class MvcContext implements Context{

    private Request request;
    private final Response methodExecutor;

    public MvcContext(Response methodExecutor) {
        this.methodExecutor = methodExecutor;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response response() {
        return methodExecutor;
    }
}