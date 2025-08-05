package com.example.tech_challenge.dtos.requests;

public record CreateUserRequest(String name, String email, String login, AddressRequest address,
                                String password, String userType) implements CreateUser {
}
