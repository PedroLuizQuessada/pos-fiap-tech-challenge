package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import static com.example.tech_challenge.helper.UserTypeHelper.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTypeTest {

    private final String invalidNameMessage = "O tipo de usuário deve possuir um nome.";

    @Test
    public void newUserTypeWithValidArgumentsShouldReturnSuccessTest() {
        UserType userType = new UserType(VALID_ID, VALID_NAME);

        assertNotNull(userType);
        assertEquals(VALID_ID, userType.getId());
        assertEquals(VALID_NAME, userType.getName());
    }

    @Test
    public void newUserTypeWithNullNameShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new UserType(VALID_ID, null), invalidNameMessage);
    }

    @Test
    public void newUserTypeWithEmptyNameShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new UserType(VALID_ID, ""), invalidNameMessage);
    }

    @Test
    public void newUserTypeWithTooLongNameShouldThrowExceptionTest() {
        String invalidNameTooLong = "Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, ";
        String invalidNameTooLongMessage = "O nome do tipo de usuário deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new UserType(VALID_ID, invalidNameTooLong), invalidNameTooLongMessage);
    }

    @Test
    public void setNameWithValidArgumentShouldReturnSuccessTest() {
        String newValidName = "Novo tipo de usuário";
        UserType userType = new UserType(VALID_ID, VALID_NAME);
        userType.setName(newValidName);

        assertNotNull(userType);
        assertEquals(VALID_ID, userType.getId());
        assertEquals(newValidName, userType.getName());
    }
}