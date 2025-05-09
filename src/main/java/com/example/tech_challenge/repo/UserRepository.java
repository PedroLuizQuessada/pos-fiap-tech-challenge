package com.example.tech_challenge.repo;

import com.example.tech_challenge.domain.user.entity.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserDB, Long> {

    UserDB findByLoginAndPassword(String login, String password);

    Integer countByEmail(String email);

    Integer countByLogin(String login);

    @Transactional
    @Modifying
    @Query("UPDATE UserDB u SET u.password = :password WHERE u.id = :id ")
    void updatePasswordById(@Param("password") String password, @Param("id") Long id);
}
