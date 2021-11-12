package com.github.kelly.web;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class ViewCompiler {

    private final String filePath;
    private final Object modelMap;
    private InputStream inputStream;


    public ViewCompiler(Object viewName, Object modelMap) {
        this.filePath = Paths.get("/templates", viewName + ".mustache").toString().replace("\\", "/");;
        this.modelMap = modelMap;
    }

    public ViewCompiler(Object viewName) {
        this.filePath = Paths.get("/templates", viewName + ".mustache").toString().replace("\\", "/");;
        this.modelMap = null;
    }

    // 파라미터 string 이 view name 이 맞는지 확인
    public boolean support() {
        // /templates/this is message.mustache
        inputStream = Mustache.class.getResourceAsStream(filePath);
        return inputStream != null;
    }

    public String compile2(Object map) {
        //
        String renderedTemplate = "";
        System.out.println("ViewCompiler - filePath = " + filePath);
        try (
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


    public String compile() {
        String renderedTemplate = "";
        System.out.println("ViewCompiler - filePath = " + filePath);
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