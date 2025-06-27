package com.example.tech_challenge.dtos.responses;

import com.example.tech_challenge.enums.AuthorityEnum;

public record LoginResponse(String login, String password, AuthorityEnum authority) {
}
