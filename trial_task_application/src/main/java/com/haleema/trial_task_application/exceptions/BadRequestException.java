package com.haleema.trial_task_application.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message)
    {
        super(message);
    }
}
