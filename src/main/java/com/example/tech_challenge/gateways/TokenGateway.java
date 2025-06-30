package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.RequesterDto;
import com.example.tech_challenge.dtos.TokenDto;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.Token;

public class TokenGateway {

    private final TokenDataSource tokenDataSource;

    public TokenGateway(TokenDataSource tokenDataSource) {
        this.tokenDataSource = tokenDataSource;
    }

    public Token generateToken(String userType, String login) {
        TokenDto tokenDto = tokenDataSource.generateToken(userType, login);
        return new Token(tokenDto.token(), tokenDto.login());
    }

    public Requester getRequester(String token) {
        RequesterDto requesterDto = tokenDataSource.getRequester(token);
        return new Requester(requesterDto.userType(), requesterDto.login());
    }
}
