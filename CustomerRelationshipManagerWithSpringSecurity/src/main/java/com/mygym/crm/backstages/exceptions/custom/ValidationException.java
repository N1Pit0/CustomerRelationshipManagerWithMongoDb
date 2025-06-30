package com.mygym.crm.backstages.exceptions.custom;

public class ValidationException extends IllegalArgumentException {
    public ValidationException(String message) {
        super(message);
    }
}
