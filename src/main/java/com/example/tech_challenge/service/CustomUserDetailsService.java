package com.example.tech_challenge.service;

import com.example.tech_challenge.domain.user.CustomUserDetails;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.enums.AuthorityEnum;
import com.example.tech_challenge.exception.UserNotFoundException;
import com.example.tech_challenge.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UserNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return new CustomUserDetails(user);
    }

    public AuthorityEnum getAuthority(String authorityText) {
        return AuthorityEnum.valueOf(authorityText.replace("Optional[", "").replace("]", ""));
    }
}
