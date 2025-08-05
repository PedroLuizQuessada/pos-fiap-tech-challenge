package com.example.tech_challenge.helper;

import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.UserJpa;

public class UserHelper {

    public static final Long VALID_ID = 1L;
    public static final String VALID_NAME = "Nome";
    public static final String VALID_EMAIL = "email@email.com";
    public static final String VALID_LOGIN = "Login";
    public static final String VALID_PASSWORD = "senha123";

    public static User getValidUser() {
        return new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD, null, AddressHelper.getValidAddress(),
                UserTypeHelper.getValidUserType(), true);
    }

    public static UserJpa getValidUserJpa() {
        return new UserJpa(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD, null, AddressHelper.getValidAddressJpa(),
                UserTypeHelper.getValidUserTypeJpa());
    }
}
