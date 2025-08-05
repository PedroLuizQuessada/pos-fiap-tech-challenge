package com.example.tech_challenge.helper;

import com.example.tech_challenge.entities.MenuItem;

public class MenuItemHelper {

    public static final Long VALID_ID = 1L;
    public static final String VALID_NAME = "Nome";
    public static final String VALID_DESCRIPTION = "Descrição";
    public static final String VALID_DESCRIPTION_2 = null;
    public static final Double VALID_PRICE = 99.90;
    public static final Boolean VALID_AVAILABLE_ONLINE = true;
    public static final String VALID_PICTURE = "foto";

    public static MenuItem getValidMenuItem() {
        return new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(), VALID_NAME, VALID_DESCRIPTION,
                VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE);
    }

}
