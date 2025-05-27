package com.example.tech_challenge.domain.user.entity;

import com.example.tech_challenge.domain.interfaces.Entity;
import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.exception.ConstraintViolationException;
import com.example.tech_challenge.utils.EncryptionUtil;
import com.example.tech_challenge.enums.AuthorityEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity {

    @Getter
    private Long id;

    @Getter
    @NotEmpty(message = "O usuário deve possuir um nome")
    @Size(min = 3, max = 45, message = "O nome do usuário deve possuir de 3 a 45 caracteres")
    private final String name;

    @Getter
    @NotEmpty(message = "O usuário deve possuir um e-mail")
    @Size(max = 45, message = "O e-mail do usuário deve possuir até 45 caracteres")
    @Email(message = "E-mail inválido")
    private final String email;

    @Getter
    @NotEmpty(message = "O usuário deve possuir um login")
    @Size(min = 3, max = 45, message = "O login do usuário deve possuir de 3 a 45 caracteres")
    @Pattern(regexp = "^[^:]+$", message = "O login não pode conter ':'")
    private final String login;

    @Getter
    @NotEmpty(message = "O usuário deve possuir uma senha")
    @Size(max = 255, message = "Houve um problema na criptografia da senha do usuário. Favor contactar o administrador do sistema.")
    private final String encodedPassword;

    private String decodedPassword;

    @Getter
    private Date lastUpdateDate;

    @Getter
    private final Address address;

    @Getter
    @NotNull(message = "O usuário deve possuir um tipo de autorização")
    private final AuthorityEnum authority;

    public User(Long id, String name, String email, String login, String encodedPassword, Date lastUpdateDate, Address address, AuthorityEnum authority) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.encodedPassword = encodedPassword;
        this.lastUpdateDate = lastUpdateDate;
        this.address = address;
        this.authority = authority;
    }

    public User(Long id, String name, String email, String login, String password, Address address,
                AuthorityEnum authority, boolean isPasswordDecoded) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.address = address;
        this.authority = authority;
        this.lastUpdateDate = new Date(new java.util.Date().getTime());

        if (isPasswordDecoded) {
            this.decodedPassword = password;
            this.encodedPassword = EncryptionUtil.encodeSha256(password);
        }
        else {
            this.encodedPassword = password;
        }
    }

    public User(String name, String email, String login, String decodedPassword, Address address, AuthorityEnum authority) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.decodedPassword = decodedPassword;
        this.encodedPassword = EncryptionUtil.encodeSha256(decodedPassword);
        this.address = address;
        this.authority = authority;
    }

    public void validateDecodedPassword() {
        List<String> constraintsMessages = new ArrayList<>();
        if (Objects.isNull(this.decodedPassword) || this.decodedPassword.length() < 3 || this.decodedPassword.length() > 45) {
            constraintsMessages.add("A senha do usuário deve possuir de 3 a 45 caracteres");
        }
        if (!Objects.isNull(this.decodedPassword) && this.decodedPassword.contains(":")) {
            constraintsMessages.add("A senha não pode conter ':'");
        }

        if (!constraintsMessages.isEmpty()) {
            throw new ConstraintViolationException(constraintsMessages);
        }
    }
}
