package com.example.tech_challenge.repo.user;

import com.example.tech_challenge.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByLoginAndPassword(String login, String password);
    Long countByEmail(String email);
    Long countByLogin(String login);
    User save(User student);
    Optional<User> findById(Long id);
    void delete(User student);
}
