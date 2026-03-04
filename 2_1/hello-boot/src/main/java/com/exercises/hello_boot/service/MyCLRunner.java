package com.exercises.hello_boot.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.context.ApplicationContext;

import com.exercises.hello_boot.service.AppInfoService;
import com.exercises.hello_boot.service.MessagePrinter;
import com.exercises.hello_boot.service.RequestTracker;

@Component
public class MyCLRunner implements CommandLineRunner {

	private final ApplicationContext appContext;
	private final MessagePrinter msgPrinter;
	private final ObjectProvider<RequestTracker> trackerProvider;
	private final AppInfoService appInfoService;

	public MyCLRunner(ApplicationContext context,
                      MessagePrinter printer,
                      ObjectProvider<RequestTracker> trackerProvider,
                      AppInfoService infoService) {
        this.appContext = context;
        this.msgPrinter = printer;
        this.trackerProvider = trackerProvider;
        this.appInfoService = infoService;
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
        
    }
    
}
