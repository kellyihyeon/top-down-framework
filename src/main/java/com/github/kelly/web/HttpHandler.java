package com.github.kelly.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.session.SessionHandler;


public class HttpHandler extends SessionHandler {

    private final DispatcherServlet dispatcherServlet;



    public HttpHandler() {
        this.dispatcherServlet = new DispatcherServlet();
    }


    @Override
    public void doHandle(String target,
                         Request baseRequest,
                         HttpServletRequest request,
                         HttpServletResponse response)
    {
        dispatcherServlet.doDispatch(request, response);
        baseRequest.setHandled(true);

    }
}
