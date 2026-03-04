package com.exercises.purespring.service;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;


@Component
//@Scope("prototype")
@Scope("singleton")
public class RequestTracker {
    public final String trackingId = UUID.randomUUID().toString();
}
