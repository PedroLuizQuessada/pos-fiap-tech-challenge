package com.example.tech_challenge.infraestructure.persistence.jpa.models;

import com.example.tech_challenge.helper.AddressHelper;
import com.example.tech_challenge.helper.UserTypeHelper;
import com.example.tech_challenge.infraestructure.exceptions.BadJpaArgumentException;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;

import static com.example.tech_challenge.helper.UserHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserJpaTest {

    private final Date validLastUpdateDate = new Date(Date.from(Instant.now()).getTime());

    @Test
    public void newUserJpaWithValidArgumentsShouldReturnSuccessTest() {
        UserJpa user = new UserJpa(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD, validLastUpdateDate,
                AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa());

        assertNotNull(user);
        assertEquals(VALID_ID, user.getId());
        assertEquals(VALID_NAME, user.getName());
        assertEquals(VALID_EMAIL, user.getEmail());
        assertEquals(VALID_LOGIN, user.getLogin());
        assertEquals(VALID_PASSWORD, user.getPassword());
        assertEquals(validLastUpdateDate, user.getLastUpdateDate());
        assertEquals(AddressHelper.getValidAddressJpa().getId(), user.getAddressJpa().getId());
        assertEquals(UserTypeHelper.getValidUserTypeJpa().getId(), user.getUserTypeJpa().getId());
    }

    @Test
    public void newUserJpaWithNoArgumentsShouldReturnSuccessTest() {
        UserJpa userJpa = new UserJpa();

        assertNotNull(userJpa);
    }

    @Test
    public void newUserJpaWithNullNameShouldThrowExceptionTest() {
        String invalidNameMessage = "O usuário deve possuir um nome para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, null, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa()),
                invalidNameMessage);
    }

    @Test
    public void newUserJpaWithTooLongNameShouldThrowExceptionTest() {
        String invalidNameTooLong = "Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, ";
        String invalidNameLengthMessage = "O nome do usuário deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, invalidNameTooLong, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa()),
                invalidNameLengthMessage);
    }

    @Test
    public void newUserJpaWithNullEmailShouldThrowExceptionTest() {
        String invalidEmailMessage = "O usuário deve possuir um e-mail para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, VALID_NAME, null, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa()),
                invalidEmailMessage);
    }

    @Test
    public void newUserJpaWithTooLongEmailShouldThrowExceptionTest() {
        String invalidEmailTooLong = "emailmuitolongoemailmuitolongoemailmuitolongoemailmuitolongo@email.com";
        String invalidEmailTooLongMessage = "O e-mail do usuário deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, VALID_NAME, invalidEmailTooLong, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa()),
                invalidEmailTooLongMessage);
    }

    @Test
    public void newUserJpaWithNullLoginShouldThrowExceptionTest() {
        String invalidLoginMessage = "O usuário deve possuir um login para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, VALID_NAME, VALID_EMAIL, null, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa()),
                invalidLoginMessage);
    }

    @Test
    public void newUserJpaWithTooLongLoginShouldThrowExceptionTest() {
        String invalidLoginTooLong = "Login muito longo, Login muito longo, Login muito longo, Login muito longo, ";
        String invalidLoginLengthMessage = "O login do usuário deve possuir até 45 caracteres para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, VALID_NAME, VALID_EMAIL, invalidLoginTooLong, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa()),
                invalidLoginLengthMessage);
    }

    @Test
    public void newUserJpaWithNullPasswordShouldThrowExceptionTest() {
        String invalidPasswordMessage = "O usuário deve possuir uma senha para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, null,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa()),
                invalidPasswordMessage);
    }

    @Test
    public void newUserJpaWithTooLongPasswordShouldThrowExceptionTest() {
        String tooLongPassword = "Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, Senha muito longa, ";
        String invalidPasswordMessage = "Falha ao gerar senha criptografada do usuário, favor contactar o administrador.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, tooLongPassword,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), UserTypeHelper.getValidUserTypeJpa()),
                invalidPasswordMessage);
    }

    @Test
    public void newUserJpaWithNullUserTypeShouldThrowExceptionTest() {
        String invalidUserTypeMessage = "O usuário deve possuir tipo de usuário para ser armazenado no banco de dados.";

        assertThrows(BadJpaArgumentException.class, () -> new UserJpa(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddressJpa(), null),
                invalidUserTypeMessage);
    }

}
