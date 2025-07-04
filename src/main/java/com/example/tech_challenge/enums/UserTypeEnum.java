package com.example.tech_challenge.enums;

import lombok.Getter;

public enum UserTypeEnum {

    ADMIN(1L),
    OWNER(3L);

    @Getter
    private final Long id;

    UserTypeEnum(Long id) {
        this.id = id;
    }
}
