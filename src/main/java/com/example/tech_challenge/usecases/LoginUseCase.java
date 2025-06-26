package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.gateways.UserGateway;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.stream.Collectors;

public class LoginUseCase {

    private JwtEncoder jwtEncoder;
    private UserGateway userGateway;

    public LoginUseCase(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public LoginUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public String execute(Authentication authentication) {
        Instant now = Instant.now();

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("tech-challenge")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .claim("scope", scopes)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public User execute(String login) {
        return userGateway.findUserByLogin(login);
    }
}
