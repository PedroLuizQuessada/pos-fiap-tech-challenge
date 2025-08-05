package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

@Getter
public class User {
    private final Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private Date lastUpdateDate;
    private Address address;
    private final UserType userType;

    public User(Long id, String name, String email, String login, String password, Date lastUpdateDate, Address address,
                UserType userType, boolean encodePassword) {

        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateEmail(email);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateLogin(login);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validatePassword(password);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateUserType(userType);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.address = address;
        this.userType = userType;

        if (encodePassword) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            this.password = encoder.encode(password);
        }
        else {
            this.password = password;
        }

        this.lastUpdateDate = !Objects.isNull(lastUpdateDate) ? lastUpdateDate : new Date(Date.from(Instant.now()).getTime());
    }

    public void setNameAndEmailAndLoginAndAddress(String name, String email, String login, Address address) {

        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateEmail(email);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateLogin(login);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.name = name;
        this.email = email;
        this.login = login;
        this.address = address;

        this.lastUpdateDate = new Date(Date.from(Instant.now()).getTime());
    }

    public void setPassword(String password) {
        validatePassword(password);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
        this.lastUpdateDate = new Date(Date.from(Instant.now()).getTime());
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty())
            throw new BadArgumentException("O usuário deve possuir um nome.");

        if (name.length() < 3 || name.length() > 45)
            throw new BadArgumentException("O nome do usuário deve possuir de 3 a 45 caracteres.");
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email) || email.isEmpty())
            throw new BadArgumentException("O usuário deve possuir um e-mail.");

        if (email.length() > 45)
            throw new BadArgumentException("O e-mail do usuário deve possuir até 45 caracteres.");

        if (!EmailValidator.getInstance().isValid(email))
            throw new BadArgumentException("E-mail inválido.");
    }

    private void validateLogin(String login) {
        if (Objects.isNull(login) || login.isEmpty())
            throw new BadArgumentException("O usuário deve possuir um login.");

        if (login.length() < 3 || login.length() > 45)
            throw new BadArgumentException("O login do usuário deve possuir de 3 a 45 caracteres.");
    }

    private void validatePassword(String password) {
        if (Objects.isNull(password) || password.isEmpty())
            throw new BadArgumentException("O usuário deve possuir uma senha.");

        if (password.length() < 6)
            throw new BadArgumentException("A senha do usuário deve possuir ao menos 6 caracteres.");
    }

    private void validateUserType(UserType userType) {
        if (Objects.isNull(userType)) {
            throw new BadArgumentException("O usuário deve possuir um tipo de usuário.");
        }
    }
}
