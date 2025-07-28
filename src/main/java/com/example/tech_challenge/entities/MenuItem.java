package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class MenuItem {
    private final Long id;
    private final Restaurant restaurant;
    private String name;
    private String description;
    private Double price;
    private Boolean availableOnline;
    private String picture;

    public MenuItem(Long id, Restaurant restaurant, String name, String description, Double price, Boolean availableOnline, String picture) {

        String message = "";

        try {
            validateRestaurant(restaurant);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateDescription(description);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validatePrice(price);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateAvailableOnline(availableOnline);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validatePicture(picture);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.id = id;
        this.restaurant = restaurant;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnline = availableOnline;
        this.picture = picture;
    }

    public void setNameAndDescriptionAndPriceAndAvailableOnlineAndPicture(String name, String description, Double price,
                                                                          Boolean availableOnline, String picture) {

        String message = "";

        try {
            validateName(name);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateDescription(description);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validatePrice(price);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validateAvailableOnline(availableOnline);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        try {
            validatePicture(picture);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadArgumentException(message);

        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnline = availableOnline;
        this.picture = picture;
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (Objects.isNull(restaurant))
            throw new BadArgumentException("O item do cardápio deve possuir um restaurante.");
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty())
            throw new BadArgumentException("O item do cardápio deve possuir um nome.");

        if (name.length() > 45)
            throw new BadArgumentException("O nome do item do cardápio deve possuir até 45 caracteres.");
    }

    private void validateDescription(String description) {
        if (description.length() > 45)
            throw new BadArgumentException("A descrição do item do cardápio deve possuir até 45 caracteres.");
    }

    private void validatePrice(Double price) {
        if (Objects.isNull(price))
            throw new BadArgumentException("O item do cardápio deve possuir um preço.");
    }

    private void validateAvailableOnline(Boolean availableOnline) {
        if (Objects.isNull(availableOnline))
            throw new BadArgumentException("O item do cardápio deve possuir uma disponibilidade para comprar on-line.");
    }

    private void validatePicture(String picture) {
        if (Objects.isNull(picture) || picture.isEmpty())
            throw new BadArgumentException("O item do cardápio deve possuir uma foto.");

        if (picture.length() > 255)
            throw new BadArgumentException("A foto do item do cardápio deve possuir até 255 caracteres.");
    }
}
