package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.helper.UserHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(userGateway.findUserByLogin(anyString())).thenReturn(UserHelper.getValidUser());

        User user = loginUseCase.execute(anyString());

        assertNotNull(user);
        assertEquals(UserHelper.getValidUser().getId(), user.getId());
        assertEquals(UserHelper.getValidUser().getName(), user.getName());
        assertEquals(UserHelper.getValidUser().getEmail(), user.getEmail());
        assertEquals(UserHelper.getValidUser().getLogin(), user.getLogin());
        assertNotEquals(UserHelper.getValidUser().getPassword(), user.getPassword());
        assertEquals(UserHelper.getValidUser().getAddress().getId(), user.getAddress().getId());
        assertEquals(UserHelper.getValidUser().getUserType().getId(), user.getUserType().getId());
    }
}
