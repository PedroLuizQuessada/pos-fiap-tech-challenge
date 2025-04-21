package com.example.tech_challenge.domain.user.dto.request;

import com.example.tech_challenge.domain.address.dto.request.AddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UserRequest {

    @NotEmpty(message = "O usuário deve possuir um nome")
    @Size(min = 3, max = 45, message = "O nome do usuário deve possuir de 3 a 45 caracteres")
    protected String name;

    @NotEmpty(message = "O usuário deve possuir um e-mail")
    @Size(max = 45, message = "O e-mail do usuário deve possuir até 45 caracteres")
    @Email(message = "E-mail inválido")
    protected String email;

    @NotEmpty(message = "O usuário deve possuir um login")
    @Size(min = 3, max = 45, message = "O login do usuário deve possuir de 3 a 45 caracteres")
    protected String login;

    @Valid
    protected AddressRequest address;
}
