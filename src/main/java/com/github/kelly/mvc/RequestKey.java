package com.github.kelly.mvc;

public class RequestKey {

    private final String key;
    private final HttpMethod httpMethod;

    public RequestKey(String key, HttpMethod httpMethod) {
        this.key = key;
        this.httpMethod = httpMethod;
    }

    @Override
    public String toString() {
        return "RequestKey{" +
                "key='" + key + '\'' +
                ", httpMethod=" + httpMethod +
                '}';
    }
}
