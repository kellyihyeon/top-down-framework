package com.github.kelly.app;

import com.github.kelly.core.Controller;
import com.github.kelly.mvc.HttpMethod;
import com.github.kelly.mvc.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    // static 으로 만들지 않으면 key 로 찾았을 때 null
    private static final Map<String, String> memberRepository = new HashMap<>();


    @RequestMapping(value = "/hello", method = HttpMethod.GET)
    public void sayHello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // controller 로 들어오고, view 를 호출해주는 쪽으로 보내기
//        DispatcherServlet.deliverToView(request, response);
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

    @RequestMapping(value = "/signUp", method = HttpMethod.GET)
    public void signUpPage(HttpServletRequest request, HttpServletResponse response) {
        String html =
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>회원가입</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "  <h1>The form element</h1>\n" +
                        "\n" +
                        "  <form action=\"/signUp\" method=\"post\">\n" +
                        "    <label for=\"id\">id:</label>\n" +
                        "    <input type=\"text\" id=\"id\" name=\"id\"><br><br>\n" +
                        "    <label for=\"password\">password:</label>\n" +
                        "    <input type=\"password\" id=\"password\" name=\"password\"><br><br>\n" +
                        "    <input type=\"submit\" value=\"Submit\">\n" +
                        "  </form>\n" +
                        "</body>\n" +
                        "</html>";

        try {
            final OutputStream os = response.getOutputStream();
            os.write(html.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/signUp", method = HttpMethod.POST)
    public void signUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String text = "";

        final String inputId = request.getParameter("id");
        final String inputPassword = request.getParameter("password");

        if (!memberRepository.containsKey(inputId)) {
            memberRepository.put(inputId, inputPassword);
            System.out.println("inputId = " + inputId + ", inputPassword = " + inputPassword);
            System.out.println(memberRepository.toString());

            text = "Success to Sign up :)";
        } else {
            text = "이미 가입되어 있는 아이디 입니다.";
        }

        try {
            OutputStream os = response.getOutputStream();

            response.setContentType("text/plain;charset=UTF-8");
            os.write(text.getBytes(StandardCharsets.UTF_8));
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        response.sendRedirect("Login");
        System.out.println("signUp method executed.");
    }


    @RequestMapping(value = "/login", method = HttpMethod.GET)
    public void loginPage(HttpServletRequest request, HttpServletResponse response) {
        String html =
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "  <h1>Log in</h1>\n" +
                        "\n" +
                        "  <form action=\"/login\" method=\"post\">\n" +
                        "    <label for=\"id\">id:</label>\n" +
                        "    <input type=\"text\" id=\"id\" name=\"id\"><br><br>\n" +
                        "    <label for=\"password\">password:</label>\n" +
                        "    <input type=\"password\" id=\"password\" name=\"password\"><br><br>\n" +
                        "    <input type=\"submit\" value=\"Submit\">\n" +
                        "  </form>\n" +
                        "</body>\n" +
                        "</html>";

        try {
            final OutputStream os = response.getOutputStream();
            os.write(html.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/login", method = HttpMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) {

            String html = "";

        final String inputId = request.getParameter("id");
        final String inputPassword = request.getParameter("password");
        System.out.println("inputId = " + inputId + ", inputPassword = " + inputPassword);

        final boolean containsKey = memberRepository.containsKey(inputId);
        final String passwordFromRepository = memberRepository.get(inputId);
        System.out.println("containsKey = " + containsKey);
        System.out.println("passwordFromRepository = " + passwordFromRepository);

        if (memberRepository.containsKey(inputId) && memberRepository.get(inputId).equals(inputPassword)) {
            html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>Success to Login !!! </h1>\n" +
                    "</body>\n" +
                    "</html>";
        } else {
            html =
                    "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "  <meta charset=\"UTF-8\">\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<h1>Fail to Login :( </h1>\n" +
                            "</body>\n" +
                            "</html>";
        }

        try {
            OutputStream os = response.getOutputStream();
            response.setContentType("text/html");

            os.write(html.getBytes(StandardCharsets.UTF_8));
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("login method executed.");
    }

    @RequestMapping(value = "/welcome", method = HttpMethod.GET)
    public void welcome() {
        System.out.println("welcome method executed.");
    }
}
