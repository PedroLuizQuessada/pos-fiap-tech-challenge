package com.example.tech_challenge.service;

import com.example.tech_challenge.domain.user.CustomUserDetails;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.enums.AuthorizationEnum;
import com.example.tech_challenge.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Login %s não encontrado", login));
        }

        return new CustomUserDetails(user);
    }

    public AuthorizationEnum getAuthorization(String authorizationText) {
        return AuthorizationEnum.valueOf(authorizationText.replace("Optional[", "").replace("]", ""));
    }
}
