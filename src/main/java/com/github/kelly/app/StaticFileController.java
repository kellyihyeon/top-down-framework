package com.github.kelly.app;

import com.github.kelly.app.utils.FileReadUtil;
import com.github.kelly.core.Controller;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class StaticFileController {

    private static final Map<String, String> MIMETYPE_MAP = new HashMap<>();
    private static final String STATIC_FILE_PATH = "static";

    public StaticFileController() {
        MIMETYPE_MAP.put("html", "text/html");
        MIMETYPE_MAP.put("text", "text/plain");
    }

    public void run(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // /hello.html
        final String uri = request.getRequestURI();
        final String filepath = STATIC_FILE_PATH + uri;
        // mime type
        final int index = uri.indexOf(".");
        final String extension = uri.substring(index + 1);   // text/html

        response.setContentType(MIMETYPE_MAP.get(extension));
        final ServletOutputStream os = response.getOutputStream();
        os.write(Objects.requireNonNull(FileReadUtil.readFileFromClasspath(filepath)));
        os.flush();

    }
}
