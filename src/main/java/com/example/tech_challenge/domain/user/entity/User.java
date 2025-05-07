package com.example.tech_challenge.domain.user.entity;

import com.example.tech_challenge.domain.address.entity.Address;
import com.example.tech_challenge.utils.EncryptionUtil;
import com.example.tech_challenge.enums.AuthorityEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class User {

    private Long id;

    @NotEmpty(message = "O usuário deve possuir um nome")
    @Size(min = 3, max = 45, message = "O nome do usuário deve possuir de 3 a 45 caracteres")
    private String name;

    @NotEmpty(message = "O usuário deve possuir um e-mail")
    @Size(max = 45, message = "O e-mail do usuário deve possuir até 45 caracteres")
    @Email(message = "E-mail inválido")
    private String email;

    @NotEmpty(message = "O usuário deve possuir um login")
    @Size(min = 3, max = 45, message = "O login do usuário deve possuir de 3 a 45 caracteres")
    @Pattern(regexp = "^[^:]+$", message = "O login não pode conter ':'")
    private String login;

    @NotEmpty(message = "O usuário deve possuir uma senha")
    @Size(min = 3, max = 45, message = "A senha do usuário deve possuir de 3 a 45 caracteres")
    @Pattern(regexp = "^[^:]+$", message = "A senha não pode conter ':'")
    private String password;

    private Date lastUpdateDate;

    @Valid
    private Address address;

    @NotNull(message = "O usuário deve possuir um tipo de autorização")
    private AuthorityEnum authority;

    public User(Long id, String name, String email, String login, String password, Address address,
                AuthorityEnum authority) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.address = address;
        this.authority = authority;
        this.lastUpdateDate = new Date(new java.util.Date().getTime());
    }

    public User(String name, String email, String login, String decodedPassword, Address address, AuthorityEnum authority) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = decodedPassword; //TODO testar validações
        this.password = EncryptionUtil.encodeSha256(decodedPassword);
        this.address = address;
        this.authority = authority;
    }

    public User(Long id, String decodedPassword) {
        this.id = id;
        this.password = decodedPassword; //TODO testar validações
        this.password = EncryptionUtil.encodeSha256(decodedPassword);
        this.lastUpdateDate = new Date(new java.util.Date().getTime());
    }

    public UserDB toEntityDB() {
        UserDB userDB = new UserDB(id, name, email, login,
                password, lastUpdateDate, address.toEntityDB(), authority);
        userDB.getAddressDB().setUserDB(userDB);
        return userDB;
    }
}
