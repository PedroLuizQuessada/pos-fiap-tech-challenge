package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Token;
import com.example.tech_challenge.gateways.TokenGateway;
import com.example.tech_challenge.helper.TokenHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenerateTokenUseCaseTest {

    @Mock
    private TokenGateway tokenGateway;

    @InjectMocks
    private GenerateTokenUseCase generateTokenUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(tokenGateway.generateToken(anyString(), anyString())).thenReturn(TokenHelper.getValidToken());

        Token token = generateTokenUseCase.execute(anyString(), anyString());

        assertNotNull(token);
        assertEquals(TokenHelper.getValidToken().getToken(), token.getToken());
        assertEquals(TokenHelper.getValidToken().getLogin(), token.getLogin());
    }
}
