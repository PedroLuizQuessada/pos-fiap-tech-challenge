package com.example.tech_challenge.dtos.request;

public interface UserRequest {
    String name();

    String email();

    String login();

    AddressRequest address();
}
