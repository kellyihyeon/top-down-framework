package com.github.kelly.app;

import com.github.kelly.core.Controller;
import com.github.kelly.web.HttpMethod;
import com.github.kelly.web.RequestMapping;
import com.github.kelly.web.ui.Model;
import com.github.kelly.web.ui.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    private static final Map<String, String> memberRepository = new HashMap<>();

    @RequestMapping("/string")
    public String outString(Model model) {
        // String attributeName, Object attributeValue
        model.addAttribute("message", "this is object value");
        model.addAttribute("myName", "Han So Hui");
        model.addAttribute("details", "in Netflix");
        return "string";
    }

    @RequestMapping("/toModelAndView")
    public ModelAndView deliverModelAndView() {
        final ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("view");
        modelAndView.addObject("title", "Framework");
        modelAndView.addObject("target", "Spring");
        modelAndView.addObject("message", "It's hard :(...");

        return modelAndView;
    }


    @RequestMapping(value = "/hello", method = HttpMethod.GET)
    public void sayHello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("hello.html");
    }

    @RequestMapping(value = "/signUp", method = HttpMethod.GET)
    public void signUpPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("signup.html");
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

        System.out.println("signUp method executed.");
    }


    @RequestMapping(value = "/login", method = HttpMethod.GET)
    public void loginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login.html");
    }


    @RequestMapping(value = "/login", method = HttpMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String html = "";

        final String inputId = request.getParameter("id");
        final String inputPassword = request.getParameter("password");
        System.out.println("inputId = " + inputId + ", inputPassword = " + inputPassword);

        final boolean containsKey = memberRepository.containsKey(inputId);
        final String passwordFromRepository = memberRepository.get(inputId);
        System.out.println("containsKey = " + containsKey);
        System.out.println("passwordFromRepository = " + passwordFromRepository);

        if (memberRepository.containsKey(inputId) && memberRepository.get(inputId).equals(inputPassword)) {
            response.sendRedirect("successToLogin.html");
        } else {
            response.sendRedirect("failToLogin.html");
        }
    }

    @RequestMapping(value = "/welcome", method = HttpMethod.GET)
    public void welcome() {
        System.out.println("welcome method executed.");
    }
}
