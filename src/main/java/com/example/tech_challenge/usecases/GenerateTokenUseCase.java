package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Token;
import com.example.tech_challenge.gateways.TokenGateway;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

public class GenerateTokenUseCase {

    private final TokenGateway tokenGateway;

    public GenerateTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public Token execute(UserDetails userDetails, String oldToken) {
        return  (!Objects.isNull(userDetails)) ? tokenGateway.generateToken(userDetails) : tokenGateway.generateToken(oldToken);
    }

}
