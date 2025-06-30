package com.example.tech_challenge.infraestructure.services;

import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.RequesterDto;
import com.example.tech_challenge.dtos.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenServiceJwtImpl implements TokenDataSource {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${jwt.token.expiration-time}")
    private Long expirationTime;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public TokenDto generateToken(String userType, String login) {
        Instant now = Instant.now();

        var claims = JwtClaimsSet.builder()
                .issuer(applicationName)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .claim("authorities", userType)
                .subject(login)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenDto(token, login);
    }

    @Override
    public RequesterDto getRequester(String token) {
        Jwt jwt = decodeToken(token);
        return new RequesterDto(jwt.getClaim("authorities"), jwt.getSubject());
    }

    private Jwt decodeToken(String token) {
        return jwtDecoder.decode(token.replace("Bearer ", ""));
    }
}
