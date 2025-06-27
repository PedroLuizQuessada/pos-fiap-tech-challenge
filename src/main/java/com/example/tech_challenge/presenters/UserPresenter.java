package com.example.tech_challenge.presenters;

import com.example.tech_challenge.dtos.responses.UserResponse;
import com.example.tech_challenge.entities.User;

import java.util.Objects;

public class UserPresenter {

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getName(), user.getEmail(), user.getLogin(), user.getLastUpdateDate(),
                !Objects.isNull(user.getAddress()) ? AddressPresenter.toResponse(user.getAddress()) : null, user.getAuthority());
    }
}
