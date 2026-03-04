package com.exercises.hello_boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="app") // this tells us which subset of properties to bind
public class AppProperties {
    String greetingPrefix; // Spring boot maps app.greeting-prefix onto this variable
    String greetingSuffix; // maps to app.greetingSuffix
    int maxResults;        // maps to app.max-results

    public String getGreetingPrefix() {
        return greetingPrefix;
    }
    public void setGreetingPrefix(String greetingPrefix) { // binding fails without setters
        this.greetingPrefix = greetingPrefix;
    }
    public String getGreetingSuffix() {
        return greetingSuffix;
    }
    public void setGreetingSuffix(String greetingSuffix) {
        this.greetingSuffix = greetingSuffix;
    }
    public int getMaxResults() {
        return maxResults;
    }
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

}
