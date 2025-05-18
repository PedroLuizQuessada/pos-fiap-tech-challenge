package com.example.tech_challenge.domain.user.dto.request;

import com.example.tech_challenge.domain.address.dto.request.AddressRequest;
import com.example.tech_challenge.enums.AuthorityEnum;

public record CreateUserRequest(String name, String email, String login, AddressRequest address,
                                String password, AuthorityEnum authority) implements UserRequest {

}
