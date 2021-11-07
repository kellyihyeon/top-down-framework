package com.github.kelly.examples;


import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MustacheExample {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(MustacheExample.class);

        final Path path = Paths.get("/templates", "index.mustache");
        // \templates\index.mustache
        log.info("path.toString() = {}", path.toString());

        try (
                // /templates/index.mustache
                final InputStream is = Mustache.class.getResourceAsStream(path.toString().replace("\\", "/"));
                final BufferedInputStream bis = new BufferedInputStream(is);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ) {
            // 읽어서 baos 에 읽은 것 넣기
            final byte[] buffer = new byte[4096];
            while (bis.read(buffer) != -1) {
                baos.write(buffer);
            }

            final String originTemplate = baos.toString(StandardCharsets.UTF_8);
            log.info("originTemplate = {} ", originTemplate);

            // mustache template 얻기
            final Mustache.Compiler compiler = Mustache.compiler();
            final Template template = compiler.compile(originTemplate);

            final Map<String, Object> map = new HashMap<>();
            map.put("message", "mustache template test.");
            map.put("myName", "HanSoHui");

            final String renderedTemplate = template.execute(map);
            log.info("renderedTemplate = {}", renderedTemplate);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
