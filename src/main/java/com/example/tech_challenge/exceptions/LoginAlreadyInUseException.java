package com.example.tech_challenge.exceptions;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;

public class LoginAlreadyInUseException extends BadRequestException {
  public LoginAlreadyInUseException() {
    super("Login já está sendo usado");
  }
}
