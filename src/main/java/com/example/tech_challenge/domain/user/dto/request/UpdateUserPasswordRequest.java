package com.example.tech_challenge.domain.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserPasswordRequest {

    @NotEmpty(message = "O usuário deve possuir uma nova senha")
    @Size(min = 3, max = 45, message = "A nova senha do usuário deve possuir de 3 a 45 caracteres")
    private String newPassword;
}
