package com.example.tech_challenge.domain.user.dto.response;

import com.example.tech_challenge.interfaces.ResponseInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse implements ResponseInterface {
    private String message;
}
