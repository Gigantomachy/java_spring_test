package com.exercises.purespring.service;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class ConnectionPool {

    // Runs after bean creation and dependencies injected
    // Runs after context is started
    @PostConstruct
    void init() {
        System.out.println("ConnectionPool initialized: opening connections...");
    }

    // runs after context.close()
    @PreDestroy
    void cleanup() {
        System.out.println("ConnectionPool destroyed: closing connections...");
    }
}
