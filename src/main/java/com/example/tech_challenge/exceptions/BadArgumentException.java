package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class BadArgumentException extends BadRequestException {
    public BadArgumentException(String message) {
        super(message);
    }
}
