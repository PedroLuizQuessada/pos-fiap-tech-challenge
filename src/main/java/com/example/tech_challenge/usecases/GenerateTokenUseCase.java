package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

public class GenerateTokenUseCase {

    private static final String TOKEN_ISSUER_NAME = "tech-challenge";
    private static final long TOKEN_EXPIRATION_TIME = 3600;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public GenerateTokenUseCase(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public Token execute(UserDetails userDetails, String oldToken) {
        if (!Objects.isNull(userDetails))
            return generateToken(userDetails);
        return generateToken(oldToken);
    }

    private Token generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        String scopes = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        String subject = userDetails.getUsername();

        var claims = JwtClaimsSet.builder()
                .issuer(TOKEN_ISSUER_NAME)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TOKEN_EXPIRATION_TIME))
                .claim("authorities", scopes)
                .subject(subject)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new Token(token, subject);
    }

    private Token generateToken(String oldToken) {
        Jwt jwt = jwtDecoder.decode(oldToken.replace("Bearer ", ""));
        Instant now = Instant.now();
        String subject = jwt.getSubject();

        var claims = JwtClaimsSet.builder()
                .issuer(TOKEN_ISSUER_NAME)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TOKEN_EXPIRATION_TIME))
                .claim("authorities", jwt.getClaim("authorities"))
                .subject(subject)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new Token(token, subject);
    }

}
