package com.exercises.purespring.service;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

@Service
public class AppInfoService {

    private final String appName;

    // @Value("${app.version}") - note this won't work because @Value injects into a field at runtime, 
    // and final fields must be assigned in the constructor
    private final String appVersion;
    private final Environment environment;
    
    public AppInfoService(@Value("${app.version}")String version, 
                          @Value("${app.name}")String name, 
                          Environment env) {
        this.appVersion = version;
        this.appName = name;
        this.environment = env;
    }

    public String getGreeting() {
        String template = environment.getProperty("app.greeting.template");
        return MessageFormat.format(template, appName, appVersion);
    }

    public String getInfo() {
        return "Running " + appName + " v" + appVersion;
    }
}
