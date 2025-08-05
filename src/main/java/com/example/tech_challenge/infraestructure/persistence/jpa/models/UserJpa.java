package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 45)
    private String name;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(45) COLLATE utf8_bin")
    private String login;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8_bin")
    private String password;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private AddressJpa addressJpa;

    @ManyToOne
    @JoinColumn(name = "user_type", referencedColumnName = "id", nullable = false)
    private UserTypeJpa userTypeJpa;

    public UserJpa(Long id, String name, String email, String login, String password, Date lastUpdateDate,
                   AddressJpa addressJpa, UserTypeJpa userTypeJpa) {

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
            validateUserTypeJpa(userTypeJpa);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadJpaArgumentException(message);

        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.lastUpdateDate = lastUpdateDate;
        this.addressJpa = addressJpa;
        this.userTypeJpa = userTypeJpa;
    }

    private void validateName(String name) {
        if (Objects.isNull(name))
            throw new BadJpaArgumentException("O usuário deve possuir um nome para ser armazenado no banco de dados.");

        if (name.length() > 45)
            throw new BadJpaArgumentException("O nome do usuário deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email))
            throw new BadJpaArgumentException("O usuário deve possuir um e-mail para ser armazenado no banco de dados.");

        if (email.length() > 45)
            throw new BadJpaArgumentException("O e-mail do usuário deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }

    private void validateLogin(String login) {
        if (Objects.isNull(login))
            throw new BadJpaArgumentException("O usuário deve possuir um login para ser armazenado no banco de dados.");

        if (login.length() > 45)
            throw new BadJpaArgumentException("O login do usuário deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }

    private void validatePassword(String password) {
        if (Objects.isNull(password))
            throw new BadJpaArgumentException("O usuário deve possuir uma senha para ser armazenado no banco de dados.");

        if (password.length() > 255)
            throw new BadJpaArgumentException("Falha ao gerar senha criptografada do usuário, favor contactar o administrador.");
    }

    private void validateUserTypeJpa(UserTypeJpa userTypeJpa) {
        if (Objects.isNull(userTypeJpa))
            throw new BadJpaArgumentException("O usuário deve possuir tipo de usuário para ser armazenado no banco de dados.");
    }
}
