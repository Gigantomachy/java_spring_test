package com.exercises.hello_boot.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

// The profiles / properties naming convention is hard coded into Spring Boot
// --spring.profiles.active=formal will make Spring Boot load application-formal.properties
@Service
public class ConfigurableGreetingService {
    
    private final String prefix;
    private final String suffix;

    public ConfigurableGreetingService(@Value("${app.greeting-prefix}")String prefix, 
                                       @Value("${app.greetingSuffix}")String suffix) 
    {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String greet(String name) {
        return prefix + ", " + name + suffix;
    }

}
