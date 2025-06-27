package com.example.tech_challenge.dtos.requests;

public record UpdateUserRequest(String name, String email, String login, AddressRequest address) implements UserRequest {

}
