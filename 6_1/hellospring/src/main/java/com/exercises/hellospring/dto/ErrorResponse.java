package com.exercises.hellospring.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timeStamp;
    private Map<String, String> validationErrors;
    
    public ErrorResponse(int status, String message, Map<String, String> validationErrors) {
        this.status = status;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
        this.validationErrors = validationErrors;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

}
