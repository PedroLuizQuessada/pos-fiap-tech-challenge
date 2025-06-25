package com.example.tech_challenge.entities;

import com.example.tech_challenge.exception.BadArgumentException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Address {
    private final Long id;
    private final String state;
    private final String city;
    private final String street;
    private final String number;
    private final String zipCode;
    private final String aditionalInfo;

    public Address(Long id, String state, String city, String street, String number, String zipCode, String aditionalInfo) {

        validateState(state);
        validateCity(city);
        validateStreet(street);
        validateNumber(number);
        validateZipCode(zipCode);
        validateAditionalInfo(aditionalInfo);

        this.id = id;
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.aditionalInfo = aditionalInfo;
    }

    private void validateState(String state) {
        if (Objects.isNull(state) || state.isEmpty())
            throw new BadArgumentException("O endereço deve possuir um estado");

        if (state.length() > 45)
            throw new BadArgumentException("O estado do endereço deve possuir até 45 caracteres");
    }

    private void validateCity(String city) {
        if (Objects.isNull(city) || city.isEmpty())
            throw new BadArgumentException("O endereço deve possuir uma cidade");

        if (city.length() > 45)
            throw new BadArgumentException("A cidade do endereço deve possuir até 45 caracteres");
    }

    private void validateStreet(String street) {
        if (Objects.isNull(street) || street.isEmpty())
            throw new BadArgumentException("O endereço deve possuir uma rua");

        if (street.length() > 45)
            throw new BadArgumentException("A rua do endereço deve possuir até 45 caracteres");
    }

    private void validateNumber(String number) {
        if (Objects.isNull(number) || number.isEmpty())
            throw new BadArgumentException("O endereço deve possuir um número");

        if (number.length() > 45)
            throw new BadArgumentException("O número do endereço deve possuir até 45 caracteres");
    }

    private void validateZipCode(String zipCode) {
        if (Objects.isNull(zipCode) || zipCode.isEmpty())
            throw new BadArgumentException("O endereço deve possuir um CEP");

        if (zipCode.length() > 45)
            throw new BadArgumentException("O CEP do endereço deve possuir até 45 caracteres");
    }

    private void validateAditionalInfo(String aditionalInfo) {
        if (aditionalInfo.length() > 45)
            throw new BadArgumentException("O complemento do endereço deve possuir até 45 caracteres");
    }
}
