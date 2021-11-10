package com.github.kelly.web;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ViewCompiler {

    private final String filePath;
    private Object modelMap;


    public ViewCompiler(Path path, Object modelMap) {
        this.filePath = path.toString().replace("\\", "/");
        this.modelMap = modelMap;
    }

    public String compile() {
        String renderedTemplate = "";
        try (
                final InputStream inputStream = Mustache.class.getResourceAsStream(filePath);
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

            renderedTemplate = compiledTemplate.execute(modelMap);
            System.out.println("renderedTemplate = " + renderedTemplate);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return renderedTemplate;
    }



}