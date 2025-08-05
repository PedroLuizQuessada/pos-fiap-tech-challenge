package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import org.junit.jupiter.api.Test;

import static com.example.tech_challenge.helper.UserTypeHelper.VALID_ID;
import static com.example.tech_challenge.helper.UserTypeHelper.VALID_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTypeJpaTest {

    @Test
    public void newUserTypeJpaWithValidArgumentsShouldReturnSuccessTest() {
        UserTypeJpa userTypeJpa = new UserTypeJpa(VALID_ID, VALID_NAME);

        assertNotNull(userTypeJpa);
        assertEquals(VALID_ID, userTypeJpa.getId());
        assertEquals(VALID_NAME, userTypeJpa.getName());
    }

    @Test
    public void newUserTypeJpaWithNoArgumentsShouldReturnSuccessTest() {
        UserTypeJpa userTypeJpa = new UserTypeJpa();

        assertNotNull(userTypeJpa);
    }

    @Test
    public void newUserTypeJpaWithNullNameShouldThrowExceptionTest() {
        String invalidNameMessage = "O tipo de usuário deve possuir um nome para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserTypeJpa(VALID_ID, null), invalidNameMessage);
    }

    @Test
    public void newUserTypeWithTooLongNameShouldThrowExceptionTest() {
        String invalidNameTooLong = "Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, ";
        String invalidNameTooLongMessage = "O nome do tipo de usuário deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserTypeJpa(VALID_ID, invalidNameTooLong), invalidNameTooLongMessage);
    }

}
