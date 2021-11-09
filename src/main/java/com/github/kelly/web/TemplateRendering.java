package com.github.kelly.web;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class TemplateRendering {


    public TemplateRendering(Path path, Object map) {
        try (
                final InputStream inputStream = Mustache.class.getResourceAsStream(path.toString().replace("\\", "/"));
                final BufferedInputStream bis = new BufferedInputStream(inputStream);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            int bufferSize;
            final byte[] buffer = new byte[4096];
            while ((bufferSize = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, bufferSize);
            }

            final String originTemplate = baos.toString(StandardCharsets.UTF_8);

            final Mustache.Compiler compiler = Mustache.compiler();
            final Template compiledTemplate = compiler.compile(originTemplate);

            final String renderedTemplate = compiledTemplate.execute(map);
            System.out.println("renderedTemplate = " + renderedTemplate);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
