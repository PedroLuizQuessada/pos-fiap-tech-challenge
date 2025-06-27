package com.example.tech_challenge.exceptions;

public class LoginAlreadyInUseException extends RuntimeException {
  public LoginAlreadyInUseException() {
    super("Login já está sendo usado");
  }
}
