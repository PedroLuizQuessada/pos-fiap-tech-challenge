package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.RequesterDto;

public interface RequesterDataSource {
    RequesterDto getRequester(String userType, String login);
}
