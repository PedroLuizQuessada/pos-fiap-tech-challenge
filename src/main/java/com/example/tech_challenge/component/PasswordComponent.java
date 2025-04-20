package com.example.tech_challenge.component;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PasswordComponent {

    private final PasswordEncoder passwordEncoder;

    public String encode(String decodedPassword) {
        return passwordEncoder.encode(decodedPassword);
    }
}
