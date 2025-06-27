package com.example.tech_challenge.infraestructure.services;

import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

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
    public TokenDto generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        String scopes = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        String subject = userDetails.getUsername();

        var claims = JwtClaimsSet.builder()
                .issuer(applicationName)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .claim("authorities", scopes)
                .subject(subject)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenDto(token, subject);
    }

    @Override
    public TokenDto generateToken(String oldToken) {
        Jwt jwt = decodeToken(oldToken);
        Instant now = Instant.now();
        String subject = jwt.getSubject();

        var claims = JwtClaimsSet.builder()
                .issuer(applicationName)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .claim("authorities", jwt.getClaim("authorities"))
                .subject(subject)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenDto(token, subject);
    }

    @Override
    public String getTokenUsername(String token) {
        return decodeToken(token).getSubject();
    }

    private Jwt decodeToken(String token) {
        return jwtDecoder.decode(token.replace("Bearer ", ""));
    }
}
