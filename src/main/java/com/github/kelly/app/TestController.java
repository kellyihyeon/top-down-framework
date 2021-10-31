package com.github.kelly.app;

import com.github.kelly.core.Controller;
import com.github.kelly.mvc.RequestMapping;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

@Controller
public class TestController {

    // RequestMapping value 값 : /hello
    @RequestMapping("/hello")
    public void sayHello(HttpServletRequest request, HttpServletResponse response) {

        response.setStatus(200);
        response.setContentType("text/html");

        try {
            OutputStream os = response.getOutputStream();
            os.write();
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
