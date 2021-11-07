package com.github.kelly.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Resolver {

    RequestHandler dispatch(HttpServletRequest request, HttpServletResponse response);
}
