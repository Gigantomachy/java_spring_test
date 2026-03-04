package com.exercises.hello_boot.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.context.ApplicationContext;

import com.exercises.hello_boot.config.AppProperties;
import com.exercises.hello_boot.service.AppInfoService;
import com.exercises.hello_boot.service.MessagePrinter;
import com.exercises.hello_boot.service.RequestTracker;

@Component
public class MyCLRunner implements CommandLineRunner {

	private final ApplicationContext appContext;
	private final MessagePrinter msgPrinter;
	private final ObjectProvider<RequestTracker> trackerProvider;
	private final AppInfoService appInfoService;
    private final ConfigurableGreetingService greetingService;
    private final AppProperties appProperties;

	public MyCLRunner(ApplicationContext context,
                      MessagePrinter printer,
                      ObjectProvider<RequestTracker> trackerProvider,
                      AppInfoService infoService,
                      ConfigurableGreetingService greetingService,
                      AppProperties properties) {
        this.appContext = context;
        this.msgPrinter = printer;
        this.trackerProvider = trackerProvider;
        this.appInfoService = infoService;
        this.greetingService = greetingService;
        this.appProperties = properties;
    }

    // Boot finishes building context ( SpringApplication.run ), wires beans, sets up server if any, then runs this method
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bean count: " + appContext.getBeanDefinitionCount());

        msgPrinter.printMessage();

        RequestTracker tracker1 = trackerProvider.getObject();
        RequestTracker tracker2 = trackerProvider.getObject();

        System.out.println("tracker1 id: " + tracker1.trackingId);
        System.out.println("tracker2 id: " + tracker2.trackingId);

        System.out.println(appInfoService.getInfo());
        System.out.println(appInfoService.getGreeting());

        System.out.println(greetingService.greet("blah blah"));
        
        System.out.println(appProperties.getGreetingPrefix() + ", " + "asdf" + appProperties.getGreetingSuffix());
        System.out.println("Max results: " + appProperties.getMaxResults());
    }
    
}
