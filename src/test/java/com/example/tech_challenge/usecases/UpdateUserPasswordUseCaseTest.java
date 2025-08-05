package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UpdateUserPasswordRequest;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.helper.RequesterHelper;
import com.example.tech_challenge.helper.UserHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserPasswordUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private TokenGateway tokenGateway;

    @InjectMocks
    private UpdateUserPasswordUseCase updateUserPasswordUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(tokenGateway.getRequester(anyString())).thenReturn(RequesterHelper.getValidRequester());
        when(userGateway.findUserByLogin(anyString())).thenReturn(UserHelper.getValidUser());
        when(userGateway.updateUser(any())).thenReturn(UserHelper.getValidUser());

        updateUserPasswordUseCase.execute(new UpdateUserPasswordRequest("nova senha"), anyString());

        verify(userGateway, times(1)).updateUser(any());
    }
}
