package com.example.tech_challenge.dtos.requests;

public interface UserRequest {
    String name();

    String email();

    String login();

    AddressRequest address();
}
