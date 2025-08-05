package com.example.tech_challenge.mappers;

import com.example.tech_challenge.dtos.responses.LoginResponse;
import com.example.tech_challenge.helper.UserHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginMapperTest {

    @Test
    public void toResponseTest() {
        LoginResponse loginResponse = LoginMapper.toResponse(UserHelper.getValidUser());

        assertNotNull(loginResponse);
    }
}
