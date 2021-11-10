package com.github.kelly.web;

import com.github.kelly.web.ui.Model;
import com.github.kelly.web.ui.ModelImpl;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import jdk.dynalink.linker.LinkerServices;
import org.reflections.Reflections;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class HandlerExecutor {

    private final Object instance;
    private final Method method;

    private Object valueOfReturn = null;
    private Object[] methodParameterArgs;


    public HandlerExecutor(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        this.methodParameterArgs = new Object[method.getParameterCount()];
    }

    // handlerExecutor.invoke(request, response);
    public Object invoke(Object... args) {   // parameters for method
        try {
            // return 타입이 없는 경우 -> controller 에서 바로 response 내려주기
            if (method.getGenericReturnType().equals(void.class)) {
                valueOfReturn = method.invoke(instance, method.getParameterCount() == 0 ? null : args);
                System.out.println("return 타입이 없는 method 반환값 = " + valueOfReturn); // null
            }
            // return 타입이 있는 경우 -> return 값 가져오기
            else {
//                valueOfReturn = getReturnValue();   //>>>>>>>>>>>>>>
//                System.out.println("따로 분류한 메소드에서 뺀 valueOfReturn 값 = " + valueOfReturn);

                final Parameter[] methodParameters = method.getParameters();


                // 1. 파라미터가 없는 경우 하지만 return 타입은 있는 경우
                if (method.getParameters().length == 0) {
                    // ModelAndView 객체 반환
                    valueOfReturn = method.invoke(instance);
                    System.out.println("파라미터가 0개인 메소드의 반환 값 = " + valueOfReturn);
                }
                // 2. 파라미터가 1개 이상이고, return 타입이 있는 경우
                else {
                    // method
                    valueOfReturn = invokeMethodWithParametersAndReturnValue(method);

//                    System.out.println("methodParameters.length = " + methodParameters.length);
//                    final Object[] objects = new Object[methodParameters.length];// 초기화
//
//                    // 파라미터 -> 인스턴스화 시키기
//                    for (Parameter parameter : methodParameters) {
//                        System.out.println("parameter.getType() = " + parameter.getType());
//                        final String classType = parameter.getType().toString().split(" ")[0];
//                        final String classPath = parameter.getType().toString().split(" ")[1];
//                        System.out.println("classType = " + classType + "/ classPath = " + classPath);
//
//                        final Class<?> initClassFromPath = Class.forName(classPath);    // parameter class type
//
//                        // parameter type 이 interface 인 경우 new Instance 할 수 없다.
//                        if (classType.equals("interface")) {
//                            final Reflections reflections = new Reflections("com.github.kelly.web");
//                            final Set<Class<?>> subTypesOf = reflections.getSubTypesOf((Class<Object>) initClassFromPath);
//                            for (Class<?> subClass : subTypesOf) {
//                                System.out.println("subClass = " + subClass);
//                                instanceOfClass = subClass.getConstructor().newInstance();
//                                System.out.println("instanceOfClass = " + instanceOfClass);
//                            }
//                        } else {
//                            instanceOfClass = initClassFromPath.getConstructor().newInstance();
//                        }
//
//                        // 파라미터 args 에 인스턴스 넣기
//                        for (int i = 0; i < methodParameters.length; i++) {
//                            objects[i] = instanceOfClass;
//                            System.out.println("objects[i] = " + objects[i]);
//                        }
//                        setMethodParameterArgs(objects);
//                        // view name 반환
//                        valueOfReturn = method.invoke(instance, objects);
//                        System.out.println("파라미터가 여러개인 메소드의 반환 값 = " + valueOfReturn);
//
//                    }   // for

                    // 템플릿 페이지로 이동 하는 경우 (Model model 을 파라미터로 넘긴 경우)
//                    final Path path = Paths.get("/templates", valueOfReturn + ".mustache");
//                    System.out.println("path = " + path);
//
//                    Object map = null;
//                    for (Object object : objects) { // 메소드 파라미터로 넘긴 args
//                        System.out.println("object = " + object);
//                        if (object instanceof Model) {
//                            for (Method method : object.getClass().getMethods()) {
//
//                                if (method.getName().startsWith("getMap")) {
//                                    final Object instance = object.getClass().getConstructor().newInstance();
//                                    map = method.invoke(instance);
//                                }
//                            }
//                        }
//                    } //

//                    new TemplateRendering(path, map);

//                    try (
//                            final InputStream inputStream = Mustache.class.getResourceAsStream(path.toString().replace("\\", "/"));
//                            final BufferedInputStream bis = new BufferedInputStream(inputStream);
//                            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    ) {
//                        int bufferSize;
//                        final byte[] buffer = new byte[4096];
//                        while ((bufferSize = bis.read(buffer)) != -1) {
//                            baos.write(buffer, 0, bufferSize);
//                        }
//
//                        final String originTemplate = baos.toString(StandardCharsets.UTF_8);
//
//                        final Mustache.Compiler compiler = Mustache.compiler();
//                        final Template compiledTemplate = compiler.compile(originTemplate);
//
//                        final String renderedTemplate = compiledTemplate.execute(map);
//                        System.out.println("renderedTemplate = " + renderedTemplate);
//
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } // else


            } //
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return valueOfReturn;
    }

    private Object invokeMethodWithParametersAndReturnValue(Method method) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Object instanceOfClass = null;
//        final Object[] objects = new Object[method.getParameterCount()];// 초기화

        // 파라미터 -> 인스턴스화 시키기
        for (Parameter parameter : method.getParameters()) {
            final String classType = parameter.getType().toString().split(" ")[0];
            final String className = parameter.getType().toString().split(" ")[1];
            System.out.println("classType = " + classType + "/ classPath = " + classType);

            final Class<?> classWithGivenName = Class.forName(className);

            if (classType.equals("interface")) {
                final Reflections reflections = new Reflections("com.github.kelly.web");
                final Set<Class<?>> subTypesOf = reflections.getSubTypesOf((Class<Object>) classWithGivenName);
                for (Class<?> subClass : subTypesOf) {
                    System.out.println("subClass = " + subClass);
                    instanceOfClass = Instantiation.instantiateWithDefaultConstructor(subClass);
                }
            } else {
                instanceOfClass = Instantiation.instantiateWithDefaultConstructor(classWithGivenName);
            }

            // 파라미터 args 에 인스턴스 넣기
            for (int i = 0; i < method.getParameterCount(); i++) {
                methodParameterArgs[i] = instanceOfClass;
                System.out.println("objects[i] = " + methodParameterArgs[i]);
            }

            setMethodParameterArgs(methodParameterArgs);
            // view name 반환
            valueOfReturn = method.invoke(instance, methodParameterArgs);
            System.out.println("파라미터가 여러개인 메소드의 반환 값 = " + valueOfReturn);
        }   // for


        return valueOfReturn;
    }

    private void setMethodParameterArgs(Object[] objects) {
        this.methodParameterArgs = objects;
    }

    public Object[] getMethodParameterArgs() {
        return methodParameterArgs;
    }
}
