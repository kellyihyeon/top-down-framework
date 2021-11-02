package com.github.kelly.mvc;

public class MvcContext implements Context{

    private final Response response;

    public MvcContext(Response response) {
        this.response = response;
    }

    @Override
    public Response response() {
        return response;
    }
}
