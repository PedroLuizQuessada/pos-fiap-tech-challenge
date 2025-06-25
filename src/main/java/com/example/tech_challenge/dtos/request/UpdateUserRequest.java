package com.example.tech_challenge.dtos.request;

public record UpdateUserRequest(String name, String email, String login, AddressRequest address) implements UserRequest {

}
