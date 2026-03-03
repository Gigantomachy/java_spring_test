package com.exercises;

import com.exercises.purespring.config.AppConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// smallest possible Spring application
// Spring only initializes internal beans (spring managed objects) because AppConfig is empty
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getBeanDefinitionCount());
        context.close();
    }
}