package com.example.tech_challenge.dtos.requests;

public record AdminCreateUserRequest(String name, String email, String login, AddressRequest address,
                                     String password, Long userType) implements CreateUser {

}
