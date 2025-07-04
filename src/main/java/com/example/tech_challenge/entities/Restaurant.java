package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Restaurant {
    private final Long id;
    private final String name;
    private final Address address;
    private final String kitchenType;
    private final String openingHours;
    private final User owner;

    public Restaurant(Long id, String name, Address address, String kitchenType, String openingHours, User owner) {

        validateName(name);
        validateKitchenType(kitchenType);
        validateOpeningHours(openingHours);
        validateOwner(owner);

        this.id = id;
        this.name = name;
        this.address = address;
        this.kitchenType = kitchenType;
        this.openingHours = openingHours;
        this.owner = owner;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty())
            throw new BadArgumentException("O restaurante deve possuir um nome");

        if (name.length() > 45)
            throw new BadArgumentException("O nome do restaurante deve possuir até 45 caracteres");
    }

    private void validateKitchenType(String kitchenType) {
        if (Objects.isNull(kitchenType) || kitchenType.isEmpty())
            throw new BadArgumentException("O restaurante deve possuir um tipo de cozinha");

        if (kitchenType.length() > 45)
            throw new BadArgumentException("O tipo de cozinha do restaurante deve possuir até 45 caracteres");
    }

    private void validateOpeningHours(String openingHours) {
        if (Objects.isNull(openingHours) || openingHours.isEmpty())
            throw new BadArgumentException("O restaurante deve possuir um horário de funcionamento");

        String regex = "^\\d{2}:\\d{2} \\d{2}:\\d{2}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(openingHours);

        if (!matcher.matches()) {
            throw new BadArgumentException("O formato do horário de funcionamento do restaurante é inválido. Exemplo de formato válido: '08:00 18:00'");
        }
    }

    private void validateOwner(User owner) {
        if (Objects.isNull(owner)) {
            throw new BadArgumentException("O restaurante deve possuir um dono");
        }
    }
}
