package com.example.tech_challenge.domain.user.entity;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.domain.address.entity.AddressDB;
import com.example.tech_challenge.exception.ConstraintViolationException;
import com.example.tech_challenge.utils.EncryptionUtil;
import com.example.tech_challenge.enums.AuthorityEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class User {

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

    @NotEmpty(message = "O usuário deve possuir uma senha")
    @Size(min = 3, max = 45, message = "A senha do usuário deve possuir de 3 a 45 caracteres")
    @Pattern(regexp = "^[^:]+$", message = "A senha não pode conter ':'")
    private String decodedPassword;

    @Getter
    private Date lastUpdateDate;

    @Getter
    private final Address address;

    @Getter
    @NotNull(message = "O usuário deve possuir um tipo de autorização")
    private final AuthorityEnum authority;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public User(Long id, String name, String email, String login, String encodedPassword, Date lastUpdateDate, Address address, AuthorityEnum authority) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.encodedPassword = encodedPassword;
        this.lastUpdateDate = lastUpdateDate;
        this.address = address;
        this.authority = authority;

        Set<ConstraintViolation<User>> userConstraintViolationHashSet = new java.util.HashSet<>(Set.of());
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "name"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "email"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "login"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "encodedPassword"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "authority"));

        if (!userConstraintViolationHashSet.isEmpty()) {
            List<String> constraintsMessages = new ArrayList<>();
            userConstraintViolationHashSet.forEach(action -> constraintsMessages.add(action.getMessage()));
            throw new ConstraintViolationException(constraintsMessages);
        }
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

        Set<ConstraintViolation<User>> userConstraintViolationHashSet = new java.util.HashSet<>(Set.of());
        if (isPasswordDecoded) {
            this.decodedPassword = password;
            this.encodedPassword = EncryptionUtil.encodeSha256(password);
            userConstraintViolationHashSet.addAll(validator.validateProperty(this, "decodedPassword"));
        }
        else {
            this.encodedPassword = password;
        }

        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "name"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "email"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "login"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "encodedPassword"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "authority"));

        if (!userConstraintViolationHashSet.isEmpty()) {
            List<String> constraintsMessages = new ArrayList<>();
            userConstraintViolationHashSet.forEach(action -> constraintsMessages.add(action.getMessage()));
            throw new ConstraintViolationException(constraintsMessages);
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

        Set<ConstraintViolation<User>> userConstraintViolationHashSet = new java.util.HashSet<>(Set.of());
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "name"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "email"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "login"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "decodedPassword"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "encodedPassword"));
        userConstraintViolationHashSet.addAll(validator.validateProperty(this, "authority"));

        if (!userConstraintViolationHashSet.isEmpty()) {
            List<String> constraintsMessages = new ArrayList<>();
            userConstraintViolationHashSet.forEach(action -> constraintsMessages.add(action.getMessage()));
            throw new ConstraintViolationException(constraintsMessages);
        }
    }

    public UserDB toEntityDB() {
        AddressDB addressDB = Objects.isNull(address) ? null : address.toEntityDB();
        UserDB userDB = new UserDB(id, name, email, login,
                encodedPassword, lastUpdateDate, addressDB, authority);
        if (!Objects.isNull(userDB.getAddressDB()))
            userDB.getAddressDB().setUserDB(userDB);
        return userDB;
    }
}
