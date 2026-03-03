package com.exercises.purespring.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exercises.purespring.service.MessageService;

@Service
// @Qualifier("casual")
public class CasualMessageService implements MessageService {

    @Override
    public String getMessage() {
        return "Hey! Spring is fun!";
    }
}
