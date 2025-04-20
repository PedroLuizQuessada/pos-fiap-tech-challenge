package com.example.tech_challenge.domain.user.dto.request;

import com.example.tech_challenge.enums.AuthorityEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest extends UserRequest {

    @NotEmpty(message = "O usuário deve possuir uma senha")
    @Size(min = 3, max = 45, message = "A senha do usuário deve possuir de 3 a 45 caracteres")
    private String password;

    @NotNull(message = "O usuário deve possuir um tipo de autorização")
    private AuthorityEnum authority;
}
