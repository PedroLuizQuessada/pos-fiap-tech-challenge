package com.example.tech_challenge.helper;

import com.example.tech_challenge.entities.Restaurant;
import com.example.tech_challenge.infraestructure.persistence.jpa.models.RestaurantJpa;

public class RestaurantHelper {

    public static final Long VALID_ID = 1L;
    public static final String VALID_NAME = "Nome";
    public static final String VALID_KITCHEN_TYPE = "Tipo de cozinha";
    public static final String VALID_OPENING_HOURS = "08:00 18:00";

    public static Restaurant getValidRestaurant() {
        return new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(), VALID_KITCHEN_TYPE, VALID_OPENING_HOURS,
                UserHelper.getValidUser());
    }

    public static RestaurantJpa getValidRestaurantJpa() {
        return new RestaurantJpa(VALID_ID, VALID_NAME, AddressHelper.getValidAddressJpa(), VALID_KITCHEN_TYPE, VALID_OPENING_HOURS,
                UserHelper.getValidUserJpa());
    }
}
