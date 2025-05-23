package com.example.tech_challenge.repo;

import com.example.tech_challenge.domain.user.entity.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDB, Long> {

    Optional<UserDB> findByLoginAndPassword(String login, String password);

    Integer countByEmail(String email);

    Integer countByLogin(String login);
}
