package com.exercises;

import com.exercises.purespring.config.AppConfig;
import com.exercises.purespring.service.MessagePrinter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// smallest possible Spring application
// Spring only initializes internal beans (spring managed objects) because AppConfig is empty
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("Bean count: " + context.getBeanDefinitionCount());

        MessagePrinter mp = context.getBean(MessagePrinter.class);
        mp.printMessage();

        context.close();
    }
}