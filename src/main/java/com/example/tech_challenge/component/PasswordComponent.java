package com.example.tech_challenge.component;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordComponent {

    private final PasswordEncoder passwordEncoder;

    public PasswordComponent(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String decodedPassword) {
        return passwordEncoder.encode(decodedPassword);
    }
}
