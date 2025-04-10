package com.example.tech_challenge.domain.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewUserRequest extends UserRequest {

    @NotEmpty(message = "O usuário deve possuir uma senha")
    @Size(min = 3, max = 45, message = "A senha do usuário deve possuir de 3 a 45 caracteres")
    private String password;
}
