package com.example.tech_challenge.repo;

import com.example.tech_challenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.login = :login")
    User findByLogin(@Param("login") String login);

    @Query("SELECT COUNT(*) FROM User u WHERE u.email = :email")
    Integer countUserByEmailEquals(@Param("email") String email);

    @Query("SELECT COUNT(*) FROM User u WHERE u.login = :login")
    Integer countUserByLoginEquals(@Param("login") String login);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.email = :email, u.login = :login, u.address = :address, " +
            "u.lastUpdateDate = :lastUpdateDate WHERE u.login = :oldLogin")
    void updateByLogin(@Param("name") String name, @Param("email") String email, @Param("login") String login,
                       @Param("address") String address, @Param("lastUpdateDate") Date lastUpdateDate, @Param("oldLogin") String oldLogin);

    @Transactional
    @Modifying
    @Query("DELETE User u WHERE u.login = :login")
    void deleteByLogin(@Param("login") String login);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.login = :login")
    void updatePasswordByLogin(@Param("password") String password, @Param("login") String login);
}
