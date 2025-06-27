package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.TokenDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenDataSource {
    TokenDto generateToken(UserDetails userDetails);
    TokenDto generateToken(String oldToken);
    String getTokenUsername(String token);
}
