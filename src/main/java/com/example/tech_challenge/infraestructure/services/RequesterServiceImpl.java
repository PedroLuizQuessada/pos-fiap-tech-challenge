package com.example.tech_challenge.infraestructure.services;

import com.example.tech_challenge.datasources.RequesterDataSource;
import com.example.tech_challenge.dtos.RequesterDto;
import org.springframework.stereotype.Service;

@Service
public class RequesterServiceImpl implements RequesterDataSource {

    @Override
    public RequesterDto getRequester(String userType, String login) {
        return new RequesterDto(userType, login);
    }
}
