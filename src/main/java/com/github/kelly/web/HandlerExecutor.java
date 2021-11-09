package com.github.kelly.web;

import org.reflections.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

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
                method.invoke(instance, method.getParameterCount() == 0 ? null : args);

            } else {
                // return 타입이 있는 경우
                // 1. 파라미터가 없는 경우
                // 2. 파라미터가 여러 개인 경우
                final Parameter[] methodParameters = method.getParameters();
                System.out.println("methodParameters.length = " + methodParameters.length);
                Object[] objects = new Object[methodParameters.length];  // 초기화
                Object instanceOfClass = null;

                // 파라미터가 여러 개인 경우
                for (Parameter parameter : methodParameters) {
                    System.out.println("parameter.getType() = " + parameter.getType());
                    final String classType = parameter.getType().toString().split(" ")[0];
                    final String classPath = parameter.getType().toString().split(" ")[1];
                    System.out.println("classType = "+ classType + "/ classPath = " + classPath);

                    final Class<?> initClassFromPath = Class.forName(classPath);    // parameter class type

                    // parameter type 이 interface 인 경우 new Instance 할 수 없다.
                    if (classType.equals("interface")) {
                        final Reflections reflections = new Reflections("com.github.kelly.web");
                        final Set<Class<?>> subTypesOf = reflections.getSubTypesOf((Class<Object>) initClassFromPath);
                        for (Class<?> subClass : subTypesOf) {
                            System.out.println("subClass = " + subClass);
                            instanceOfClass = subClass.getConstructor().newInstance();
                        }
                    } else {
                        instanceOfClass = initClassFromPath.getConstructor().newInstance();
                    }

                    for (int i = 0; i < methodParameters.length; i++) {
                        objects[i] = instanceOfClass;
                        System.out.println("objects[i] = " + objects[i]);
                    }
                }   // for

                if (methodParameters.length == 0) {
                    final Object result = method.invoke(instance);
                    System.out.println("파라미터가 0개인 메소드의 반환 값 = " + result);
                } else {
                    final Object result = method.invoke(instance, objects); //
                    System.out.println("파라미터가 여러개인 메소드의 반환 값 = " + result);

                }


                // 파라미터가 없는 경우
//                if (method.getParameterCount() == 0) {
//                    method.invoke(instance);    // >> 수정
//
//
//                } else {    // 파라미터가 있는 경우 ( default 로)
//                    Object[] parameterForMethodInvoke = new Object[method.getParameterCount()];
//                    Object object;
//
//                    // 파라미터를 읽는다 -> 인스턴스화 시킨다. -> method.invoke 의 객체로 전달한다.
//                    for (int i = 0; i < method.getParameterCount(); i++) {
//                        final Parameter[] parameters = method.getParameters();
//                        final String[] splitParameter = parameters[i].toString().split(" ");
//                        final String path = splitParameter[0];
//                        System.out.println("path = " + path);
//
//                        final Class<?> extractedClass = Class.forName(path);
//                        System.out.println("extractedClass = " + extractedClass.getName());
//                        parameterForMethodInvoke[i] = extractedClass;
//                        System.out.println("object[] 에 들어있는 거 꺼내기 = " + parameterForMethodInvoke[i]);
//                        for (Method mt : extractedClass.getMethods()) {
//                            System.out.println("method.getName() = " + mt.getName());
//                        }
//                    }
//
////                    Model model = new ModelImpl();
//
//                    final Object valueOfReturn = method.invoke(instance, parameterForMethodInvoke);// mapping 에서 args 를 넣어주는 경우 말고는 방법이 없는 것 같다.
//                    System.out.println("valueOfReturn = " + valueOfReturn);
//
//                    final Path path = Paths.get("/templates", valueOfReturn + ".mustache");
//                    System.out.println("path = " + path);
//
//                    try (
//                            final InputStream inputStream = Mustache.class.getResourceAsStream(path.toString().replace("\\", "/"));
//                            final BufferedInputStream bis = new BufferedInputStream(inputStream);
//                            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    ) {
//                        final byte[] buffer = new byte[4096];
//                        while (bis.read(buffer) != -1) {
//                            baos.write(buffer);
//                        }
//
//                        final String originTemplate = baos.toString(StandardCharsets.UTF_8);
//
//                        final Mustache.Compiler compiler = Mustache.compiler();
//                        final Template compiledTemplate = compiler.compile(originTemplate);
//
//
////                        final String renderedTemplate = compiledTemplate.execute();
////                        // response 가 있어야 내보내는데?
////                        System.out.println("renderedTemplate = " + renderedTemplate);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//
//                }
            }

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
