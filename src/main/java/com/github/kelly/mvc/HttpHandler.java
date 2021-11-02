package com.github.kelly.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.session.SessionHandler;

import java.io.IOException;
import java.util.Map;

public class HttpHandler extends SessionHandler {

    DispatcherServlet dispatcherServlet;
    private final Map<RequestKey, RequestHandler> handlerMap2;

    public HttpHandler(Map<RequestKey, RequestHandler> handlerMap2) {
        this.handlerMap2 = handlerMap2;
        this.dispatcherServlet = new DispatcherServlet(handlerMap2);
    }


    @Override
    public void doHandle(String target,
                         Request baseRequest,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException
    {
        dispatcherServlet.doDispatch(request, response);
        baseRequest.setHandled(true);

    }
}
