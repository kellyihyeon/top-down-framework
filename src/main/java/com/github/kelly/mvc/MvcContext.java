package com.github.kelly.mvc;

public class MvcContext implements Context{

    private Request request;

    private final Response response;

    public MvcContext(Response response) {
        this.response = response;
    }

    public MvcContext(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Response response() {
        return response;
    }
}
