package com.github.kelly;

import com.github.kelly.core.ComponentScan;
import com.github.kelly.mvc.SpringApplication;

@ComponentScan(
        basePackage = "com.github.kelly.app"
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
