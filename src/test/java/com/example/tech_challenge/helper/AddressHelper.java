package com.example.tech_challenge.helper;

import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.AddressJpa;

public class AddressHelper {

    public static final Long VALID_ID = 1L;
    public static final String VALID_STATE = "Estado";
    public static final String VALID_CITY = "Cidade";
    public static final String VALID_STREET = "Rua";
    public static final String VALID_NUMBER = "Número";
    public static final String VALID_ZIP_CODE = "abc123";
    public static final String VALID_ADITIONAL_INFO = "Complemento";

    public static Address getValidAddress() {
        return new Address(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO);
    }

    public static AddressJpa getValidAddressJpa() {
        return new AddressJpa(VALID_ID, VALID_STATE, VALID_CITY, VALID_STREET, VALID_NUMBER, VALID_ZIP_CODE, VALID_ADITIONAL_INFO);
    }
}
