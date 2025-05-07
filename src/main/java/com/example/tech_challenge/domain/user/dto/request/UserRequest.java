package com.example.tech_challenge.domain.user.dto.request;

import com.example.tech_challenge.domain.address.dto.request.AddressRequest;
import lombok.Getter;

@Getter
public class UserRequest {

    protected String name;

    protected String email;

    protected String login;

    protected AddressRequest address;
}
