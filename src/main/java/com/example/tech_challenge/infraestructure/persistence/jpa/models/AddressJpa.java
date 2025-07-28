package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "addresses")
@Getter
@NoArgsConstructor
public class AddressJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_state", nullable = false, length = 45)
    private String state;

    @Column(nullable = false, length = 45)
    private String city;

    @Column(nullable = false, length = 45)
    private String street;

    @Column(name = "address_number", nullable = false, length = 45)
    private String number;

    @Column(name = "zip_code", nullable = false, length = 45)
    private String zipCode;

    @Column(name = "aditional_info", length = 45)
    private String aditionalInfo;

    public AddressJpa(Long id, String state, String city, String street, String number, String zipCode, String aditionalInfo) {

        String message = "";

        try {
            validateState(state);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateCity(city);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateStreet(street);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateNumber(number);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateZipCode(zipCode);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateAditionalInfo(aditionalInfo);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadJpaArgumentException(message);

        this.id = id;
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.aditionalInfo = aditionalInfo;
    }

    private void validateState(String state) {
        if (Objects.isNull(state))
            throw new BadJpaArgumentException("O endereço deve possuir um estado para ser armazenado no banco de dados.");

        if (state.length() > 45)
            throw new BadJpaArgumentException("O estado do endereço deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }

    private void validateCity(String city) {
        if (Objects.isNull(city))
            throw new BadJpaArgumentException("O endereço deve possuir uma cidade para ser armazenado no banco de dados.");

        if (city.length() > 45)
            throw new BadJpaArgumentException("A cidade do endereço deve possuir até 45 caracteres para ser armazenada no banco de dados.");
    }

    private void validateStreet(String street) {
        if (Objects.isNull(street))
            throw new BadJpaArgumentException("O endereço deve possuir uma rua para ser armazenado no banco de dados.");

        if (street.length() > 45)
            throw new BadJpaArgumentException("A rua do endereço deve possuir até 45 caracteres para ser armazenada no banco de dados.");
    }

    private void validateNumber(String number) {
        if (Objects.isNull(number))
            throw new BadJpaArgumentException("O endereço deve possuir um número para ser armazenado no banco de dados.");

        if (number.length() > 45)
            throw new BadJpaArgumentException("O número do endereço deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }

    private void validateZipCode(String zipCode) {
        if (Objects.isNull(zipCode))
            throw new BadJpaArgumentException("O endereço deve possuir um CEP para ser armazenado no banco de dados.");

        if (zipCode.length() > 45)
            throw new BadJpaArgumentException("O CEP do endereço deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }

    private void validateAditionalInfo(String aditionalInfo) {
        if (aditionalInfo.length() > 45)
            throw new BadJpaArgumentException("O complemento do endereço deve possuir até 45 caracteres para ser armazenado no banco de dados.");
    }
}
