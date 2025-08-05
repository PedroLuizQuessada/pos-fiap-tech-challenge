package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.helper.RestaurantHelper;
import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemJpaTest {

    private final Long validId = 1L;
    private final String validName = "Nome";
    private final String validDescription = "Descrição";
    private final String validDescription2 = null;
    private final Double validPrice = 99.90;
    private final Boolean validAvailableOnline = true;
    private final String validPicture = "foto";
    private final String invalidDescriptionTooLong = "Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, ";

    @Test
    public void newMenuItemJpaWithValidArgumentsShouldReturnSuccessTest() {
        MenuItemJpa menuItemJpa = new MenuItemJpa(validId, RestaurantHelper.getValidRestaurantJpa(), validName, validDescription,
                validPrice, validAvailableOnline, validPicture);

        assertNotNull(menuItemJpa);
        assertEquals(validId, menuItemJpa.getId());
        assertEquals(RestaurantHelper.getValidRestaurantJpa().getId(), menuItemJpa.getRestaurantJpa().getId());
        assertEquals(validName, menuItemJpa.getName());
        assertEquals(validDescription, menuItemJpa.getDescription());
        assertEquals(validPrice, menuItemJpa.getPrice());
        assertEquals(validAvailableOnline, menuItemJpa.getAvailableOnline());
        assertEquals(validPicture, menuItemJpa.getPicture());
    }

    @Test
    public void newMenuItemJpaWithNoArgumentsShouldReturnSuccessTest() {
        MenuItemJpa menuItemJpa = new MenuItemJpa();

        assertNotNull(menuItemJpa);
    }

    @Test
    public void newMenuItemJpaWithNullRestaurantShouldThrowExceptionTest() {
        String invalidRestaurantMessage = "O item do cardápio deve possuir um restaurante para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new MenuItemJpa(validId, null, validName, validDescription,
                validPrice, validAvailableOnline, validPicture), invalidRestaurantMessage);
    }

    @Test
    public void newMenuItemJpaWithNullNameShouldThrowExceptionTest() {
        String invalidNameMessage = "O item do cardápio deve possuir um nome para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new MenuItemJpa(validId, RestaurantHelper.getValidRestaurantJpa(),
                null, validDescription2, validPrice, validAvailableOnline, validPicture), invalidNameMessage);
    }

    @Test
    public void newMenuItemJpaWithTooLongNameShouldThrowExceptionTest() {
        String invalidNameTooLong = "Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, ";
        String invalidNameTooLongMessage = "O nome do item do cardápio deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new MenuItemJpa(validId, RestaurantHelper.getValidRestaurantJpa(),
                invalidNameTooLong, validDescription, validPrice, validAvailableOnline, validPicture), invalidNameTooLongMessage);
    }

    @Test
    public void newMenuItemJpaWithTooLongDescriptionShouldThrowExceptionTest() {
        String invalidDescriptionTooLongMessage = "A descrição do item do cardápio deve possuir até 255 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new MenuItemJpa(validId, RestaurantHelper.getValidRestaurantJpa(),
                validName, invalidDescriptionTooLong, validPrice, validAvailableOnline, validPicture), invalidDescriptionTooLongMessage);
    }

    @Test
    public void newMenuItemJpaWithNullPriceShouldThrowExceptionTest() {
        String invalidPriceMessage = "O item do cardápio deve possuir um preço para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new MenuItemJpa(validId, RestaurantHelper.getValidRestaurantJpa(),
                validName, validDescription, null, validAvailableOnline, validPicture), invalidPriceMessage);
    }

    @Test
    public void newMenuItemJpaWithNullAvailableOnlineShouldThrowExceptionTest() {
        String invalidAvailableOnlineMessage = "O item do cardápio deve possuir um status para disponibilidade on-line para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new MenuItemJpa(validId, RestaurantHelper.getValidRestaurantJpa(),
                validName, validDescription, validPrice, null, validPicture), invalidAvailableOnlineMessage);
    }

    @Test
    public void newMenuItemJpaWithNullPictureShouldThrowExceptionTest() {
        String invalidPictureMessage = "O item do cardápio deve possuir uma imagem para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new MenuItemJpa(validId, RestaurantHelper.getValidRestaurantJpa(),
                validName, validDescription, validPrice, validAvailableOnline, null), invalidPictureMessage);
    }

    @Test
    public void newMenuItemJpaWithTooLongPictureShouldThrowExceptionTest() {
        String invalidPictureTooLong = "Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, ";
        String invalidPictureTooLongMessage = "A imagem do item do cardápio deve possuir até 255 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new MenuItemJpa(validId, RestaurantHelper.getValidRestaurantJpa(),
                validName, validDescription, validPrice, validAvailableOnline, invalidPictureTooLong), invalidPictureTooLongMessage);
    }

}
