package com.example.tech_challenge.domain.user.dto.request;

import com.example.tech_challenge.enums.AuthorityEnum;
import lombok.Getter;

@Getter
public class CreateUserRequest extends UserRequest {

    private String password;

    private AuthorityEnum authority;
}
