package com.exercises.hello_boot.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exercises.hello_boot.service.MessageService;

@Service
// @Qualifier("casual")
public class CasualMessageService implements MessageService {

    @Override
    public String getMessage() {
        return "Hey! Spring is fun!";
    }
}
