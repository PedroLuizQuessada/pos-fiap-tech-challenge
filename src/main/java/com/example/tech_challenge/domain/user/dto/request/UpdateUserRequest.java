package com.example.tech_challenge.domain.user.dto.request;

import com.example.tech_challenge.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.sql.Date;
import java.util.Objects;

@Getter
public class UpdateUserRequest extends UserRequest {

    @NotEmpty(message = "O usuário deve possuir um e-mail antigo")
    @Size(max = 45, message = "O antigo e-mail do usuário deve possuir até 45 caracteres")
    @Email(message = "Antigo e-mail inválido")
    private String oldEmail;

    @NotEmpty(message = "O usuário deve possuir um login antigo")
    @Size(min = 3, max = 45, message = "O antigo login do usuário deve possuir de 3 a 45 caracteres")
    private String oldLogin;

    @Override
    public User requestToEntity() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setLogin(this.login);
        if (!Objects.isNull(this.address))
            user.setAddress(this.address.requestToEntity());
        user.setLastUpdateDate(new Date(System.currentTimeMillis()));
        return user;
    }
}
