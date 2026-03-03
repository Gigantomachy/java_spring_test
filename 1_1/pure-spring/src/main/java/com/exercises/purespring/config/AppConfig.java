package com.exercises.purespring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exercises.purespring.service.MessageService;
import com.exercises.purespring.service.MessagePrinter;

// Inversion of Control - Spring is smart enough to know that these @Bean methods return objects
// spring stores these returned objects in the application context and 
// spring wires dependencies by matching method parameters to other beans
// Each @Bean method becomes a bean definition in the container
@Configuration
public class AppConfig {

    @Bean
    MessageService getMessageService() {
        return new MessageService();
    }

    // Spring injects the dependency for us
    @Bean
    MessagePrinter getMessagePrinter(MessageService ms) {
        return new MessagePrinter(ms);
    }
}
