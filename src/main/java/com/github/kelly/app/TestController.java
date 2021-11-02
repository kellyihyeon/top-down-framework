package com.github.kelly.app;

import com.github.kelly.core.Controller;
import com.github.kelly.mvc.HttpMethod;
import com.github.kelly.mvc.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class TestController {

    @RequestMapping(value = "/hello", method = HttpMethod.GET)
    public void sayHello(HttpServletResponse response) {

        String html =
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Hello Page</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Welcome, anonymous!</h1>\n" +
                "</body>\n" +
                "</html>";

        response.setStatus(200);
        response.setContentType("text/html");

        try {
            OutputStream os = response.getOutputStream();
            os.write(html.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/signUp", method = HttpMethod.POST)
    public void signUp(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("signUp method executed.");
    }

    @RequestMapping(value = "/welcome", method = HttpMethod.GET)
    public void welcome() {
        System.out.println("welcome method executed.");
    }
}
