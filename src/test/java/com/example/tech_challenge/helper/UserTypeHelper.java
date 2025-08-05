package com.example.tech_challenge.helper;

import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.UserTypeJpa;

public class UserTypeHelper {

    public static final Long VALID_ID = 1L;
    public static final String VALID_NAME = "Tipo de usuário";

    public static UserType getValidUserType() {
        return new UserType(VALID_ID, VALID_NAME);
    }

    public static UserTypeJpa getValidUserTypeJpa() {
        return new UserTypeJpa(VALID_ID, VALID_NAME);
    }
}
