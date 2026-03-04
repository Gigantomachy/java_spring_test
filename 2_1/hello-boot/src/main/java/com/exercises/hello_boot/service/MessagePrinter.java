package com.exercises.hello_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.exercises.hello_boot.service.MessageService;

@Component
public class MessagePrinter {
    private final MessageService messageService;

    // @Autowired
    // public MessagePrinter(@Qualifier("casualMessageService") MessageService ms) {
    //     this.messageService = ms;
    // }

    @Autowired
    public MessagePrinter(MessageService ms) {
        this.messageService = ms;
    }

    public void printMessage() {
        System.out.println(messageService.getMessage());
    }
}
