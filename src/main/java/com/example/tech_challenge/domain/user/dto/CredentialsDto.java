package com.example.tech_challenge.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CredentialsDto {
    private String login;
    private String password;
}
