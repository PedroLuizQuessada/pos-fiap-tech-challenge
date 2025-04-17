package com.example.tech_challenge.domain.user.dto.request;

import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.interfaces.RequestInterface;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserPasswordRequest implements RequestInterface {
    @NotEmpty(message = "O usuário deve possuir uma nova senha")
    @Size(min = 3, max = 45, message = "A nova senha do usuário deve possuir de 3 a 45 caracteres")
    private String newPassword;

    @Override
    public User requestToEntity() {
        User user = new User();
        user.setPassword(this.newPassword);
        return user;
    }
}
