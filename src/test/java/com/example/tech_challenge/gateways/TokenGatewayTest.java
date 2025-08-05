package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.RequesterDto;
import com.example.tech_challenge.dtos.TokenDto;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenGatewayTest {

    private TokenDataSource tokenDataSource;
    private TokenGateway tokenGateway;

    @BeforeEach
    void setUp() {
        tokenDataSource = mock(TokenDataSource.class);
        tokenGateway = new TokenGateway(tokenDataSource);
    }

    @Test
    void shouldGenerateToken() {
        String userType = "ADMIN";
        String login = "admin@example.com";

        TokenDto tokenDto = new TokenDto("generated-token-123", login);
        when(tokenDataSource.generateToken(userType, login)).thenReturn(tokenDto);

        Token result = tokenGateway.generateToken(userType, login);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo("generated-token-123");
        assertThat(result.getLogin()).isEqualTo(login);
    }

    @Test
    void shouldGetRequester() {
        String token = "token-abc";
        RequesterDto requesterDto = new RequesterDto("CUSTOMER", "john.doe@example.com");

        when(tokenDataSource.getRequester(token)).thenReturn(requesterDto);

        Requester result = tokenGateway.getRequester(token);

        assertThat(result).isNotNull();
        assertThat(result.getUserType()).isEqualTo("CUSTOMER");
        assertThat(result.getLogin()).isEqualTo("john.doe@example.com");
    }
}
