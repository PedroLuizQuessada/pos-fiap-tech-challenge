package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Restaurant {
    private final Long id;
    private String name;
    private Address address;
    private String kitchenType;
    private String openingHours;
    private final User owner;

    public Restaurant(Long id, String name, Address address, String kitchenType, String openingHours, User owner) {

        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateKitchenType(kitchenType);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateOpeningHours(openingHours);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateOwner(owner);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.id = id;
        this.name = name;
        this.address = address;
        this.kitchenType = kitchenType;
        this.openingHours = openingHours;
        this.owner = owner;
    }

    public void setNameAndAddressAndKitchenTypeAndOpeningHours(String name, Address address, String kitchenType, String openingHours) {
        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateKitchenType(kitchenType);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateOpeningHours(openingHours);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.name = name;
        this.address = address;
        this.kitchenType = kitchenType;
        this.openingHours = openingHours;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty())
            throw new BadArgumentException("O restaurante deve possuir um nome.");

        if (name.length() > 45)
            throw new BadArgumentException("O nome do restaurante deve possuir até 45 caracteres.");
    }

    private void validateKitchenType(String kitchenType) {
        if (Objects.isNull(kitchenType) || kitchenType.isEmpty())
            throw new BadArgumentException("O restaurante deve possuir um tipo de cozinha.");

        if (kitchenType.length() > 45)
            throw new BadArgumentException("O tipo de cozinha do restaurante deve possuir até 45 caracteres.");
    }

    private void validateOpeningHours(String openingHours) {
        if (Objects.isNull(openingHours) || openingHours.isEmpty())
            throw new BadArgumentException("O restaurante deve possuir um horário de funcionamento.");

        String regex = "^\\d{2}:\\d{2} \\d{2}:\\d{2}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(openingHours);

        if (!matcher.matches()) {
            throw new BadArgumentException("O formato do horário de funcionamento do restaurante é inválido. Exemplo de formato válido: '08:00 18:00'.");
        }
    }

    private void validateOwner(User owner) {
        if (Objects.isNull(owner)) {
            throw new BadArgumentException("O restaurante deve possuir um dono.");
        }
    }
}
