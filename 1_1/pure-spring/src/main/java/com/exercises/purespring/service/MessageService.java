package com.exercises.purespring.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public String getMessage() {
        return "Hello from Spring Framework!";
    }
}
