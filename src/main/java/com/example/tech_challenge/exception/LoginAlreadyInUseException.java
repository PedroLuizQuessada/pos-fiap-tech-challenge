package com.example.tech_challenge.exception;

public class LoginAlreadyInUseException extends RuntimeException {
  public LoginAlreadyInUseException() {
    super("Login já está sendo usado");
  }
}
