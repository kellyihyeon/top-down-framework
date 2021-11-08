package com.github.kelly.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.session.SessionHandler;


public class JettyHandler extends SessionHandler {

    private final DispatcherServlet dispatcherServlet;

    public JettyHandler(DispatcherServlet dispatcherServlet) {
        this.dispatcherServlet = dispatcherServlet;
    }

    @Override
    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
        dispatcherServlet.doDispatch(request, response);
        baseRequest.setHandled(true);
    }
}
