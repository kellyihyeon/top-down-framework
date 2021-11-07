package com.github.kelly.app;

import com.github.kelly.core.ComponentScan;
import com.github.kelly.core.SpringApplication;

@ComponentScan(
        basePackage = "com.github.kelly.app"
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
