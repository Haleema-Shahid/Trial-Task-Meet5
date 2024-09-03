package com.haleema.trial_task_application.exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message)
    {
        super(message);
    }
}
