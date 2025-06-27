package com.example.tech_challenge.dtos.requests;

import com.example.tech_challenge.enums.AuthorityEnum;

public record CreateUserRequest(String name, String email, String login, AddressRequest address,
                                String password, AuthorityEnum authority) implements UserRequest {

}
