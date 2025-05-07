package com.example.tech_challenge.domain.address.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Address {

    private Long id;

    @NotEmpty(message = "O endereço deve possuir um estado")
    @Size(max = 45, message = "O estado do endereço deve possuir até 45 caracteres")
    private String state;

    @NotEmpty(message = "O endereço deve possuir uma cidade")
    @Size(max = 45, message = "A cidade do endereço deve possuir até 45 caracteres")
    private String city;

    @NotEmpty(message = "O endereço deve possuir uma rua")
    @Size(max = 45, message = "A rua do endereço deve possuir até 45 caracteres")
    private String street;

    @NotEmpty(message = "O endereço deve possuir um número")
    @Size(max = 45, message = "O número do endereço deve possuir até 45 caracteres")
    private String number;

    @NotEmpty(message = "O endereço deve possuir um CEP")
    @Size(max = 45, message = "O CEP do endereço deve possuir até 45 caracteres")
    private String zipCode;

    @Size(max = 45, message = "O complemento do endereço deve possuir até 45 caracteres")
    private String aditionalInfo;

    public Address(String state, String city, String street, String number, String zipCode, String aditionalInfo) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.aditionalInfo = aditionalInfo;
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
