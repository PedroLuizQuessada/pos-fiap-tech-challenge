package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import com.example.tech_challenge.helper.AddressHelper;
import com.example.tech_challenge.helper.UserHelper;
import org.junit.jupiter.api.Test;

import static com.example.tech_challenge.helper.RestaurantHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {

    private final String invalidNameMessage = "O restaurante deve possuir um nome.";
    private final String invalidKitchenTypeMessage = "O restaurante deve possuir um tipo de cozinha.";
    private final String invalidOpeningHoursMessage = "O restaurante deve possuir um horário de funcionamento.";

    @Test
    public void newRestaurantWithValidArgumentsShouldReturnSuccessTest() {
        Restaurant restaurant = new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(), VALID_KITCHEN_TYPE,
                VALID_OPENING_HOURS, UserHelper.getValidUser());

        assertNotNull(restaurant);
        assertEquals(VALID_ID, restaurant.getId());
        assertEquals(VALID_NAME, restaurant.getName());
        assertEquals(AddressHelper.getValidAddress().getId(), restaurant.getAddress().getId());
        assertEquals(VALID_KITCHEN_TYPE, restaurant.getKitchenType());
        assertEquals(VALID_OPENING_HOURS, restaurant.getOpeningHours());
        assertEquals(UserHelper.getValidUser().getId(), restaurant.getOwner().getId());
    }

    @Test
    public void newRestaurantWithNullNameShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, null, AddressHelper.getValidAddress(),
                        VALID_KITCHEN_TYPE, VALID_OPENING_HOURS, UserHelper.getValidUser()), invalidNameMessage);
    }

    @Test
    public void newRestaurantWithEmptyNameShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, "", AddressHelper.getValidAddress(),
                VALID_KITCHEN_TYPE, VALID_OPENING_HOURS, UserHelper.getValidUser()), invalidNameMessage);
    }

    @Test
    public void newRestaurantWithTooLongNameShouldThrowExceptionTest() {
        String invalidNameTooLong = "Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, ";
        String invalidNameTooLongMessage = "O nome do restaurante deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, invalidNameTooLong, AddressHelper.getValidAddress(),
                        VALID_KITCHEN_TYPE, VALID_OPENING_HOURS, UserHelper.getValidUser()), invalidNameTooLongMessage);
    }

    @Test
    public void newRestaurantWithNullKitchenTypeShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(),
                null, VALID_OPENING_HOURS, UserHelper.getValidUser()), invalidKitchenTypeMessage);
    }

    @Test
    public void newRestaurantWithEmptyKitchenTypeShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(),
                "", VALID_OPENING_HOURS, UserHelper.getValidUser()), invalidKitchenTypeMessage);
    }

    @Test
    public void newRestaurantWithTooLongKitchenTypeShouldThrowExceptionTest() {
        String invalidKitchenTypeTooLong = "Tipo de cozinha muito longo, Tipo de cozinha muito longo, Tipo de cozinha muito longo, Tipo de cozinha muito longo, ";
        String invalidKitchenTypeTooLongMessage = "O tipo de cozinha do restaurante deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(),
                invalidKitchenTypeTooLong, VALID_OPENING_HOURS, UserHelper.getValidUser()), invalidKitchenTypeTooLongMessage);
    }

    @Test
    public void newRestaurantWithNullOpeningHoursShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(),
                VALID_KITCHEN_TYPE, null, UserHelper.getValidUser()), invalidOpeningHoursMessage);
    }

    @Test
    public void newRestaurantWithEmptyOpeningHoursShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(),
                VALID_KITCHEN_TYPE, "", UserHelper.getValidUser()), invalidOpeningHoursMessage);
    }

    @Test
    public void newRestaurantWithInvalidOpeningHoursShouldThrowExceptionTest() {
        String invalidOpeningHoursPatternMessage = "O formato do horário de funcionamento do restaurante é inválido. Exemplo de formato válido: '08:00 18:00'.";

        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(),
                VALID_KITCHEN_TYPE, "abc", UserHelper.getValidUser()), invalidOpeningHoursPatternMessage);
    }

    @Test
    public void newRestaurantWithNullOwnerShouldThrowExceptionTest() {
        String invalidOwnerMessage = "O restaurante deve possuir um dono.";

        assertThrows(BadArgumentException.class, () -> new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(),
                VALID_KITCHEN_TYPE, VALID_OPENING_HOURS, null), invalidOwnerMessage);
    }

    @Test
    public void setNameAndAddressAndKitchenTypeAndOpeningHoursWithValidArgumentsShouldReturnSuccessTest() {
        Restaurant restaurant = new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(), VALID_KITCHEN_TYPE,
                VALID_OPENING_HOURS, UserHelper.getValidUser());
        String newName = "Novo Nome";
        Address newAddress = null;
        String newKitchenType = "Novo tipo de cozinha";
        String newOpeningHours = "12:00 23:00";
        restaurant.setNameAndAddressAndKitchenTypeAndOpeningHours(newName, newAddress, newKitchenType, newOpeningHours);

        assertEquals(newName, restaurant.getName());
        assertEquals(newAddress, restaurant.getAddress());
        assertEquals(newKitchenType, restaurant.getKitchenType());
        assertEquals(newOpeningHours, restaurant.getOpeningHours());
    }

    @Test
    public void setNameAndAddressAndKitchenTypeAndOpeningHoursWithInvalidArgumentsShouldThrowExceptionTest() {
        Restaurant restaurant = new Restaurant(VALID_ID, VALID_NAME, AddressHelper.getValidAddress(), VALID_KITCHEN_TYPE,
                VALID_OPENING_HOURS, UserHelper.getValidUser());

        assertThrows(BadArgumentException.class, () ->
                restaurant.setNameAndAddressAndKitchenTypeAndOpeningHours("", null, null, "xyz"));
    }
}
