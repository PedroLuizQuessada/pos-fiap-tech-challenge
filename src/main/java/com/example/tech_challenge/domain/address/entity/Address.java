package com.example.tech_challenge.domain.address.entity;

import com.example.tech_challenge.domain.interfaces.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class Address extends Entity {

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

    public Address(Long id, String state, String city, String street, String number, String zipCode, String aditionalInfo) {
        this.id = id;
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.aditionalInfo = aditionalInfo;
    }

    public Address(String state, String city, String street, String number, String zipCode, String aditionalInfo) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.aditionalInfo = aditionalInfo;
    }
}
