package com.example.tech_challenge.enums;

import lombok.Getter;

public enum AuthorityEnum {

    ADMIN(1L);

    @Getter
    private final Long id;

    AuthorityEnum(Long id) {
        this.id = id;
    }
}
