package com.exercises.hello_boot.service;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;

import com.exercises.hello_boot.service.MessageService;

@Service
@Primary
public class FormalMessageService implements MessageService {
    
    @Override
    public String getMessage() {
        return "Good day. Welcome to the Spring Framework.";
    }
}
