package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.helper.AddressHelper;
import com.example.tech_challenge.helper.UserHelper;
import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import org.junit.jupiter.api.Test;

import static com.example.tech_challenge.helper.RestaurantHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class RestaurantJpaTest {

    @Test
    public void newRestaurantJpaWithValidArgumentsShouldReturnSuccessTest() {
        RestaurantJpa restaurantJpa = new RestaurantJpa(VALID_ID, VALID_NAME, AddressHelper.getValidAddressJpa(), VALID_KITCHEN_TYPE,
                VALID_OPENING_HOURS, UserHelper.getValidUserJpa());

        assertNotNull(restaurantJpa);
        assertEquals(VALID_ID, restaurantJpa.getId());
        assertEquals(VALID_NAME, restaurantJpa.getName());
        assertEquals(AddressHelper.getValidAddressJpa().getId(), restaurantJpa.getAddressJpa().getId());
        assertEquals(VALID_KITCHEN_TYPE, restaurantJpa.getKitchenType());
        assertEquals(VALID_OPENING_HOURS, restaurantJpa.getOpeningHours());
        assertEquals(UserHelper.getValidUserJpa().getId(), restaurantJpa.getUserJpa().getId());
    }

    @Test
    public void newRestaurantJpaWithNoArgumentsShouldReturnSuccessTest() {
        RestaurantJpa restaurantJpa = new RestaurantJpa();

        assertNotNull(restaurantJpa);
    }

    @Test
    public void newRestaurantJpaWithNullNameShouldThrowExceptionTest() {
        String invalidNameMessage = "O restaurante deve possuir um nome para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new RestaurantJpa(VALID_ID, null, AddressHelper.getValidAddressJpa(),
                VALID_KITCHEN_TYPE, VALID_OPENING_HOURS, UserHelper.getValidUserJpa()), invalidNameMessage);
    }

    @Test
    public void newRestaurantJpaWithTooLongNameShouldThrowExceptionTest() {
        String invalidNameTooLong = "Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, ";
        String invalidNameTooLongMessage = "O nome do restaurante deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new RestaurantJpa(VALID_ID, invalidNameTooLong, AddressHelper.getValidAddressJpa(),
                VALID_KITCHEN_TYPE, VALID_OPENING_HOURS, UserHelper.getValidUserJpa()), invalidNameTooLongMessage);
    }

    @Test
    public void newRestaurantJpaWithNullKitchenTypeShouldThrowExceptionTest() {
        String invalidKitchenTypeMessage = "O restaurante deve possuir um tipo de cozinha para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new RestaurantJpa(VALID_ID, VALID_NAME, AddressHelper.getValidAddressJpa(),
                null, VALID_OPENING_HOURS, UserHelper.getValidUserJpa()), invalidKitchenTypeMessage);
    }

    @Test
    public void newRestaurantJpaWithTooLongKitchenTypeShouldThrowExceptionTest() {
        String invalidKitchenTypeTooLong = "Tipo de cozinha muito longo, Tipo de cozinha muito longo, Tipo de cozinha muito longo, Tipo de cozinha muito longo, ";
        String invalidKitchenTypeTooLongMessage = "O tipo de cozinha do restaurante deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new RestaurantJpa(VALID_ID, VALID_NAME, AddressHelper.getValidAddressJpa(),
                invalidKitchenTypeTooLong, VALID_OPENING_HOURS, UserHelper.getValidUserJpa()), invalidKitchenTypeTooLongMessage);
    }

    @Test
    public void newRestaurantJpaWithNullOpeningHoursShouldThrowExceptionTest() {
        String invalidOpeningHoursMessage = "O restaurante deve possuir um horário de funcionamento para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new RestaurantJpa(VALID_ID, VALID_NAME, AddressHelper.getValidAddressJpa(),
                VALID_KITCHEN_TYPE, null, UserHelper.getValidUserJpa()), invalidOpeningHoursMessage);
    }

    @Test
    public void newRestaurantJpaWithNullOwnerShouldThrowExceptionTest() {
        String invalidOwnerMessage = "O restaurante deve possuir um dono para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new RestaurantJpa(VALID_ID, VALID_NAME, AddressHelper.getValidAddressJpa(),
                VALID_KITCHEN_TYPE, VALID_OPENING_HOURS, null), invalidOwnerMessage);
    }

}
