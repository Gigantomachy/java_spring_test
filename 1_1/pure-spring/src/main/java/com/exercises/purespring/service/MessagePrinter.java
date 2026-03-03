package com.exercises.purespring.service;

import com.exercises.purespring.service.MessageService;

public class MessagePrinter {
    private final MessageService messageService;

    public MessagePrinter(MessageService ms) {
        this.messageService = ms;
    }

    public void printMessage() {
        System.out.println(messageService.getMessage());
    }
}
