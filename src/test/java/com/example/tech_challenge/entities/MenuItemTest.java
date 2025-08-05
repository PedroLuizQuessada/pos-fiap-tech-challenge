package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import com.example.tech_challenge.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;

import static com.example.tech_challenge.helper.MenuItemHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class MenuItemTest {

    private final String invalidNameMessage = "O item do cardápio deve possuir um nome.";
    private final String invalidDescriptionTooLong = "Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, Descrição muito longo, ";
    private final String invalidPictureMessage = "O item do cardápio deve possuir uma foto.";

    @Test
    public void newMenuItemWithValidArgumentsShouldReturnSuccessTest() {
        MenuItem menuItem = new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(), VALID_NAME, VALID_DESCRIPTION,
                VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE);

        assertNotNull(menuItem);
        assertEquals(VALID_ID, menuItem.getId());
        assertEquals(RestaurantHelper.getValidRestaurant().getId(), menuItem.getRestaurant().getId());
        assertEquals(VALID_NAME, menuItem.getName());
        assertEquals(VALID_DESCRIPTION, menuItem.getDescription());
        assertEquals(VALID_PRICE, menuItem.getPrice());
        assertEquals(VALID_AVAILABLE_ONLINE, menuItem.getAvailableOnline());
        assertEquals(VALID_PICTURE, menuItem.getPicture());
    }

    @Test
    public void newMenuItemWithNullRestaurantShouldThrowExceptionTest() {
        String invalidRestaurantMessage = "O item do cardápio deve possuir um restaurante.";

        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, null, VALID_NAME, VALID_DESCRIPTION,
                VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE), invalidRestaurantMessage);
    }

    @Test
    public void newMenuItemWithNullNameShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                null, VALID_DESCRIPTION_2, VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE), invalidNameMessage);
    }

    @Test
    public void newMenuItemWithEmptyNameShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                "", VALID_DESCRIPTION_2, VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE), invalidNameMessage);
    }

    @Test
    public void newMenuItemWithTooLongNameShouldThrowExceptionTest() {
        String invalidNameTooLong = "Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, ";
        String invalidNameTooLongMessage = "O nome do item do cardápio deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                invalidNameTooLong, VALID_DESCRIPTION, VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE), invalidNameTooLongMessage);
    }

    @Test
    public void newMenuItemWithTooLongDescriptionShouldThrowExceptionTest() {
        String invalidDescriptionTooLongMessage = "O nome do item do cardápio deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                VALID_NAME, invalidDescriptionTooLong, VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE), invalidDescriptionTooLongMessage);
    }

    @Test
    public void newMenuItemWithNullPriceShouldThrowExceptionTest() {
        String invalidPriceMessage = "O item do cardápio deve possuir um preço.";

        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                VALID_NAME, VALID_DESCRIPTION, null, VALID_AVAILABLE_ONLINE, VALID_PICTURE), invalidPriceMessage);
    }

    @Test
    public void newMenuItemWithNullAvailableOnlineShouldThrowExceptionTest() {
        String invalidAvailableOnlineMessage = "O item do cardápio deve possuir uma disponibilidade para comprar on-line.";

        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, null, VALID_PICTURE), invalidAvailableOnlineMessage);
    }

    @Test
    public void newMenuItemWithNullPictureShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_AVAILABLE_ONLINE, null), invalidPictureMessage);
    }

    @Test
    public void newMenuItemWithEmptyPictureShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                "", VALID_DESCRIPTION, VALID_PRICE, VALID_AVAILABLE_ONLINE, ""), invalidPictureMessage);
    }

    @Test
    public void newMenuItemWithTooLongPictureShouldThrowExceptionTest() {
        String invalidPictureTooLong = "Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, Foto do item muita longa, ";
        String invalidPictureTooLongMessage = "A foto do item do cardápio deve possuir até 255 caracteres.";

        assertThrows(BadArgumentException.class, () -> new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(),
                VALID_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_AVAILABLE_ONLINE, invalidPictureTooLong), invalidPictureTooLongMessage);
    }

    @Test
    public void setNameAndDescriptionAndPriceAndAvailableOnlineAndPictureWithValidArgumentsShouldReturnSuccessTest() {
        MenuItem menuItem = new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(), VALID_NAME, VALID_DESCRIPTION,
                VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE);
        String newName = "Novo Nome";
        String newDescription = "Nova descrição";
        Double newPrice = 10D;
        Boolean newAvailableOnline = false;
        String newPicture = "Nova foto";
        menuItem.setNameAndDescriptionAndPriceAndAvailableOnlineAndPicture(newName, newDescription, newPrice, newAvailableOnline, newPicture);

        assertEquals(newName, menuItem.getName());
        assertEquals(newDescription, menuItem.getDescription());
        assertEquals(newPrice, menuItem.getPrice());
        assertEquals(newAvailableOnline, menuItem.getAvailableOnline());
        assertEquals(newPicture, menuItem.getPicture());
    }

    @Test
    public void setNameAndEmailAndLoginAndAddressWithInvalidArgumentsShouldThrowExceptionTest() {
        MenuItem menuItem = new MenuItem(VALID_ID, RestaurantHelper.getValidRestaurant(), VALID_NAME, VALID_DESCRIPTION,
                VALID_PRICE, VALID_AVAILABLE_ONLINE, VALID_PICTURE);

        assertThrows(BadArgumentException.class, () ->
                menuItem.setNameAndDescriptionAndPriceAndAvailableOnlineAndPicture(null, invalidDescriptionTooLong, null, null, null));
    }
}
