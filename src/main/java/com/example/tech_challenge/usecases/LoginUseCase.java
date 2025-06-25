package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.CredentialsDto;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.exception.AuthenticationException;
import com.example.tech_challenge.exception.AuthorityException;
import com.example.tech_challenge.exception.UserNotFoundException;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.utils.EncryptionUtil;

public class LoginUseCase {

    private final UserGateway userGateway;

    public LoginUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(String authToken, boolean onlyAdmin) {
        CredentialsDto credentialsDto = getCredentialsByAuthToken(authToken);
        User user;
        try {
            user = userGateway.findUserByLoginAndPassword(credentialsDto.login(), credentialsDto.password());
        }
        catch (UserNotFoundException e) {
            throw new AuthenticationException();
        }

        if (onlyAdmin && !user.getAuthority().equals(AuthorityEnum.ADMIN))
            throw new AuthorityException();

        return user;
    }

    private CredentialsDto getCredentialsByAuthToken(String authToken) {
        String[] decodedCredentials = EncryptionUtil.decodeBase64(authToken.replace("Basic ", "")).split(":");
        return new CredentialsDto(decodedCredentials[0], EncryptionUtil.encodeSha256(decodedCredentials[1]));
    }
}
