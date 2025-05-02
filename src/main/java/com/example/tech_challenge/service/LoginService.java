package com.example.tech_challenge.service;

import com.example.tech_challenge.component.security.EncryptionComponent;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.domain.user.dto.CredentialsDto;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.exception.AuthenticationException;
import com.example.tech_challenge.exception.AuthorityException;
import com.example.tech_challenge.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;
    private final EncryptionComponent encryptionComponent;

    public User login(String authToken, boolean onlyAdmin) {

        if (Objects.isNull(authToken))
            throw new AuthenticationException();

        CredentialsDto credentialsDto = getCredentialsByAuthToken(authToken);
        User user;
        try {
            user = userService.getUserByLoginAndPassword(credentialsDto.getLogin(), credentialsDto.getPassword());
        }
        catch (UserNotFoundException e) {
            throw new AuthenticationException();
        }

        if (onlyAdmin && !user.getAuthority().equals(AuthorityEnum.ADMIN))
            throw new AuthorityException();

        return user;
    }

    private CredentialsDto getCredentialsByAuthToken(String authToken) {
        String[] decodedCredentials = encryptionComponent.decodeBase64(authToken.replace("Basic ", "")).split(":");

        return new CredentialsDto(decodedCredentials[0], encryptionComponent.encodeSha256(decodedCredentials[1]));
    }
}
