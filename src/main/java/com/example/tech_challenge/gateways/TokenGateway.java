package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.TokenDto;
import com.example.tech_challenge.entities.Token;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenGateway {

    private final TokenDataSource tokenDataSource;

    public TokenGateway(TokenDataSource tokenDataSource) {
        this.tokenDataSource = tokenDataSource;
    }

    public Token generateToken(UserDetails userDetails) {
        TokenDto tokenDto = tokenDataSource.generateToken(userDetails);
        return new Token(tokenDto.token(), tokenDto.login());
    }

    public Token generateToken(String oldToken) {
        TokenDto tokenDto = tokenDataSource.generateToken(oldToken);
        return new Token(tokenDto.token(), tokenDto.login());
    }

    public String getTokenUsername(String token) {
        return tokenDataSource.getTokenUsername(token);
    }
}
