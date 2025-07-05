package com.example.tech_challenge.enums;

import lombok.Getter;

import java.util.Objects;

public enum UserTypeEnum {

    ADMIN(1L),
    OWNER(2L);

    @Getter
    private final Long id;

    UserTypeEnum(Long id) {
        this.id = id;
    }

    public static UserTypeEnum getUserTypeById(Long id) {
        for (UserTypeEnum userType : UserTypeEnum.values()) {
            if (Objects.equals(id, userType.id))
                return userType;
        }
        return null;
    }
}
