package com.example.tech_challenge.domain.user.dto.request;

import com.example.tech_challenge.domain.address.dto.request.AddressRequest;

public record UpdateUserRequest(String name, String email, String login, AddressRequest address) implements UserRequest {

}
