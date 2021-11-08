package com.github.kelly.core;

// default server frame
public interface WebServer {

    int getPort();

    void start() throws Exception;

}
