package com.github.kelly.mvc;

import java.util.Objects;

public class RequestKey {

    private final String path;
    private final HttpMethod httpMethod;

    public RequestKey(String key, HttpMethod httpMethod) {
        this.path = key;
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestKey that = (RequestKey) o;
        return Objects.equals(path, that.path) && httpMethod == that.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, httpMethod);
    }

    @Override
    public String toString() {
        return "RequestKey{" +
                "key='" + path + '\'' +
                ", httpMethod=" + httpMethod +
                '}';
    }
}
