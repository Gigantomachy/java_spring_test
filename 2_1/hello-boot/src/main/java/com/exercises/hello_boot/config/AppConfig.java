package com.exercises.hello_boot.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.PropertySource;

// import com.exercises.hello_boot.service.MessageService;
// import com.exercises.hello_boot.service.MessagePrinter;

// Inversion of Control - Spring is smart enough to know that these @Bean methods return objects
// spring stores these returned objects in the application context and 
// spring wires dependencies by matching method parameters to other beans
// Each @Bean method becomes a bean definition in the container
@Configuration
// @ComponentScan(basePackages = "com.exercises.purespring")
// @PropertySource("classpath:app.properties") // Maven automatically puts src/main/resources on the classpath
public class AppConfig {

    // @Bean
    // MessageService getMessageService() {
    //     return new MessageService();
    // }

    // // Spring injects the dependency for us
    // @Bean
    // MessagePrinter getMessagePrinter(MessageService ms) {
    //     return new MessagePrinter(ms);
    // }

    // Use @Bean when building complex objects, or need deterministic wiring, or
    // when it comes from a third party library (Can't annotate ourselves)
}
