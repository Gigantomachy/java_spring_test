package com.exercises;

import com.exercises.purespring.config.AppConfig;
import com.exercises.purespring.service.AppInfoService;
import com.exercises.purespring.service.MessagePrinter;
import com.exercises.purespring.service.RequestTracker;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// smallest possible Spring application
// Spring only initializes internal beans (spring managed objects) because AppConfig is empty
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("Bean count: " + context.getBeanDefinitionCount());

        MessagePrinter mp = context.getBean(MessagePrinter.class);
        mp.printMessage();

        RequestTracker tracker1 = context.getBean(RequestTracker.class);
        RequestTracker tracker2 = context.getBean(RequestTracker.class);

        System.out.println("tracker1 id: " + tracker1.trackingId);
        System.out.println("tracker2 id: " + tracker2.trackingId);

        AppInfoService appInfoService = context.getBean(AppInfoService.class);

        System.out.println(appInfoService.getInfo());
        System.out.println(appInfoService.getGreeting());

        context.close();
    }
}