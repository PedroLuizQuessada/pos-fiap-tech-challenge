package com.example.tech_challenge.domain.user;

import com.example.tech_challenge.enums.AuthorizationEnum;
import com.example.tech_challenge.interfaces.ResponseInterface;
import com.example.tech_challenge.interfaces.EntityInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements ResponseInterface, Serializable {

    private String name;

    private String email;

    private String login;

    private Date lastUpdateDate;

    private String address;

    private AuthorizationEnum authorization;

    @Override
    public EntityInterface responseToEntity() {
        return new User(name, email, login, lastUpdateDate, address, authorization);
    }
}
