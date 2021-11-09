package com.github.kelly.web;

import com.github.kelly.web.ui.Model;
import com.github.kelly.web.ui.ModelImpl;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HandlerExecutor {

    private final Object instance;
    private final Method method;

    public HandlerExecutor(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public void invoke(Object ... args) {   // parameters for method
        try {

            if (method.getGenericReturnType().equals(void.class)) {     // return 타입이 없는 경우
                if (method.getParameterCount() == 0) {
                    method.invoke(instance);
                } else {
                    method.invoke(instance, args);  // mapping 에서 args 를 넣어주는 경우 말고는 방법이 없는 것 같다.
                }

            } else {    // return 타입이 있는 경우
                final Type returnType = method.getGenericReturnType();  // class java.lang.String
                // 파라미터가 없는 경우
                if (method.getParameterCount() == 0) {
                    method.invoke(instance);
                } else {    // 파라미터가 있는 경우
                    Model model = new ModelImpl();

                    final Object valueOfReturn = method.invoke(instance, model);// mapping 에서 args 를 넣어주는 경우 말고는 방법이 없는 것 같다.
                    System.out.println("valueOfReturn = " + valueOfReturn);

                    final Path path = Paths.get("/templates", valueOfReturn + ".mustache");
                    System.out.println("path = " + path);

                    try (
                            final InputStream inputStream = Mustache.class.getResourceAsStream(path.toString().replace("\\", "/"));
                            final BufferedInputStream bis = new BufferedInputStream(inputStream);
                            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ) {
                        final byte[] buffer = new byte[4096];
                        while (bis.read(buffer) != -1) {
                            baos.write(buffer);
                        }

                        final String originTemplate = baos.toString(StandardCharsets.UTF_8);

                        final Mustache.Compiler compiler = Mustache.compiler();
                        final Template compiledTemplate = compiler.compile(originTemplate);

                        final String renderedTemplate = compiledTemplate.execute(model.getMap());
                        // response 가 있어야 내보내는데?
                        System.out.println("renderedTemplate = " + renderedTemplate);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
