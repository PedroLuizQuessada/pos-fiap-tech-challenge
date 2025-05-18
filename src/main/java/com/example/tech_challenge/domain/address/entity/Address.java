package com.example.tech_challenge.domain.address.entity;

import com.example.tech_challenge.exception.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Address {

    @Getter
    private Long id;

    @Getter
    @NotEmpty(message = "O endereço deve possuir um estado")
    @Size(max = 45, message = "O estado do endereço deve possuir até 45 caracteres")
    private final String state;

    @Getter
    @NotEmpty(message = "O endereço deve possuir uma cidade")
    @Size(max = 45, message = "A cidade do endereço deve possuir até 45 caracteres")
    private final String city;

    @Getter
    @NotEmpty(message = "O endereço deve possuir uma rua")
    @Size(max = 45, message = "A rua do endereço deve possuir até 45 caracteres")
    private final String street;

    @Getter
    @NotEmpty(message = "O endereço deve possuir um número")
    @Size(max = 45, message = "O número do endereço deve possuir até 45 caracteres")
    private final String number;

    @Getter
    @NotEmpty(message = "O endereço deve possuir um CEP")
    @Size(max = 45, message = "O CEP do endereço deve possuir até 45 caracteres")
    private final String zipCode;

    @Getter
    @Size(max = 45, message = "O complemento do endereço deve possuir até 45 caracteres")
    private final String aditionalInfo;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Address(Long id, String state, String city, String street, String number, String zipCode, String aditionalInfo) {
        this.id = id;
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.aditionalInfo = aditionalInfo;

        Set<ConstraintViolation<Address>> constraintViolationHashSet = validator.validate(this);
        if (!constraintViolationHashSet.isEmpty()) {
            List<String> constraintsMessages = new ArrayList<>();
            constraintViolationHashSet.forEach(action -> constraintsMessages.add(action.getMessage()));
            throw new ConstraintViolationException(constraintsMessages);
        }
    }

    public Address(String state, String city, String street, String number, String zipCode, String aditionalInfo) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.aditionalInfo = aditionalInfo;

        Set<ConstraintViolation<Address>> constraintViolationHashSet = validator.validate(this);
        if (!constraintViolationHashSet.isEmpty()) {
            List<String> constraintsMessages = new ArrayList<>();
            constraintViolationHashSet.forEach(action -> constraintsMessages.add(action.getMessage()));
            throw new ConstraintViolationException(constraintsMessages);
        }
    }

    public AddressDB toEntityDB() {
        AddressDB addressDB = new AddressDB();
        addressDB.setId(id);
        addressDB.setState(state);
        addressDB.setCity(city);
        addressDB.setStreet(street);
        addressDB.setNumber(number);
        addressDB.setZipCode(zipCode);
        addressDB.setAditionalInfo(aditionalInfo);
        return addressDB;
    }
}
