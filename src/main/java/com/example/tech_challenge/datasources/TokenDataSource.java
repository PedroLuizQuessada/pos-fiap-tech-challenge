package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.RequesterDto;
import com.example.tech_challenge.dtos.TokenDto;

public interface TokenDataSource {
    TokenDto generateToken(String userType, String login);
    RequesterDto getRequester(String token);
}
