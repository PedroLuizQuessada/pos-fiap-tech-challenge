package com.example.tech_challenge.exception;

import java.util.List;

public class ConstraintViolationException extends RuntimeException {
    public ConstraintViolationException(List<String> constraintsMessages) {
        StringBuilder message = new StringBuilder();
        constraintsMessages.forEach(constraintMessage -> message.append(constraintMessage).append(". "));
        throw new ConstraintViolationException(String.valueOf(message));
    }

    private ConstraintViolationException(String message) {
        super(message);
    }
}
