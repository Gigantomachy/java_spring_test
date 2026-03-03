package com.exercises.purespring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exercises.purespring.service.MessageService;

@Component
public class MessagePrinter {
    private final MessageService messageService;

    @Autowired
    public MessagePrinter(MessageService ms) {
        this.messageService = ms;
    }

    public void printMessage() {
        System.out.println(messageService.getMessage());
    }
}
