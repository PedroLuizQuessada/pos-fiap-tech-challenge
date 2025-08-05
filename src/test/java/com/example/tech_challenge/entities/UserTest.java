package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import com.example.tech_challenge.helper.AddressHelper;
import com.example.tech_challenge.helper.UserTypeHelper;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;

import static com.example.tech_challenge.helper.UserHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private final Date validLastUpdateDate = new Date(Date.from(Instant.now()).getTime());
    private final String invalidNameMessage = "O usuário deve possuir um nome.";
    private final String invalidNameLengthMessage = "O nome do usuário deve possuir de 3 a 45 caracteres.";
    private final String invalidEmailMessage = "O usuário deve possuir um e-mail.";
    private final String invalidLoginMessage = "O usuário deve possuir um login.";
    private final String invalidLoginTooShort = "Lo";
    private final String invalidLoginLengthMessage = "O login do usuário deve possuir de 3 a 45 caracteres.";
    private final String invalidPasswordMessage = "O usuário deve possuir uma senha.";

    @Test
    public void newUserWithValidArgumentsPasswordDecodedNotNullLastUpdateDateShouldReturnSuccessTest() {
        User user = new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD, validLastUpdateDate,
                AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true);

        assertNotNull(user);
        assertEquals(VALID_ID, user.getId());
        assertEquals(VALID_NAME, user.getName());
        assertEquals(VALID_EMAIL, user.getEmail());
        assertEquals(VALID_LOGIN, user.getLogin());
        assertNotEquals(VALID_PASSWORD, user.getPassword());
        assertEquals(validLastUpdateDate, user.getLastUpdateDate());
        assertEquals(AddressHelper.getValidAddress().getId(), user.getAddress().getId());
        assertEquals(UserTypeHelper.getValidUserType().getId(), user.getUserType().getId());
    }

    @Test
    public void newUserWithValidArgumentsPasswordEncodedNullLastUpdateDateShouldReturnSuccessTest() {
        User user = new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD, null,
                AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), false);

        assertNotNull(user);
        assertEquals(VALID_ID, user.getId());
        assertEquals(VALID_NAME, user.getName());
        assertEquals(VALID_EMAIL, user.getEmail());
        assertEquals(VALID_LOGIN, user.getLogin());
        assertEquals(VALID_PASSWORD, user.getPassword());
        assertNotNull(user.getLastUpdateDate());
        assertEquals(AddressHelper.getValidAddress().getId(), user.getAddress().getId());
        assertEquals(UserTypeHelper.getValidUserType().getId(), user.getUserType().getId());
    }

    @Test
    public void newUserWithNullNameShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, null, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidNameMessage);
    }

    @Test
    public void newUserWithEmptyNameShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, "", VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidNameMessage);
    }

    @Test
    public void newUserWithTooShortNameShouldThrowExceptionTest() {
        String invalidNameTooShort = "No";

        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, invalidNameTooShort, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidNameLengthMessage);
    }

    @Test
    public void newUserWithTooLongNameShouldThrowExceptionTest() {
        String invalidNameTooLong = "Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, Nome muito longo, ";

        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, invalidNameTooLong, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidNameLengthMessage);
    }

    @Test
    public void newUserWithNullEmailShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, null, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidEmailMessage);
    }

    @Test
    public void newUserWithEmptyEmailShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, "", VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidEmailMessage);
    }

    @Test
    public void newUserWithTooLongEmailShouldThrowExceptionTest() {
        String invalidEmailTooLong = "emailmuitolongoemailmuitolongoemailmuitolongoemailmuitolongo@email.com";
        String invalidEmailTooLongMessage = "O e-mail do usuário deve possuir até 45 caracteres.";

        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, invalidEmailTooLong, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidEmailTooLongMessage);
    }

    @Test
    public void newUserWithInvalidEmailShouldThrowExceptionTest() {
        String invalidEmail = "emailinvalido";

        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, invalidEmail, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidEmailMessage);
    }

    @Test
    public void newUserWithNullLoginShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, VALID_EMAIL, null, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidLoginMessage);
    }

    @Test
    public void newUserWithEmptyLoginShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, VALID_EMAIL, "", VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidLoginMessage);
    }

    @Test
    public void newUserWithTooShortLoginShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, VALID_EMAIL, invalidLoginTooShort, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidLoginLengthMessage);
    }

    @Test
    public void newUserWithTooLongLoginShouldThrowExceptionTest() {
        String invalidLoginTooLong = "Login muito longo, Login muito longo, Login muito longo, Login muito longo, ";

        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, VALID_EMAIL, invalidLoginTooLong, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidLoginLengthMessage);
    }

    @Test
    public void newUserWithNullPasswordShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, null,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidPasswordMessage);
    }

    @Test
    public void newUserWithEmptyPasswordShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, "",
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), false),
                invalidPasswordMessage);
    }

    @Test
    public void newUserWithTooShortPasswordShouldThrowExceptionTest() {
        String invalidPasswordTooShort = "senha";
        String invalidPasswordTooShortMessage = "A senha do usuário deve possuir ao menos 6 caracteres.";

        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, VALID_EMAIL, invalidLoginTooShort, invalidPasswordTooShort,
                        validLastUpdateDate, AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true),
                invalidPasswordTooShortMessage);
    }

    @Test
    public void newUserWithNullUserTypeShouldThrowExceptionTest() {
        String invalidUserTypeMessage = "O usuário deve possuir um tipo de usuário.";

        assertThrows(BadArgumentException.class, () -> new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD,
                        validLastUpdateDate, AddressHelper.getValidAddress(), null, true),
                invalidUserTypeMessage);
    }

    @Test
    public void setNameAndEmailAndLoginAndAddressWithValidArgumentsShouldReturnSuccessTest() {
        User user = new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD, null,
                AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true);
        String newName = "Novo Nome";
        String newEmail = "novoemail@email.com";
        String newLogin = "NovoLogin";
        Address newAddress = null;
        user.setNameAndEmailAndLoginAndAddress(newName, newEmail, newLogin, newAddress);

        assertEquals(newName, user.getName());
        assertEquals(newEmail, user.getEmail());
        assertEquals(newLogin, user.getLogin());
        assertEquals(newAddress, user.getAddress());
        assertNotNull(user.getLastUpdateDate());
    }

    @Test
    public void setNameAndEmailAndLoginAndAddressWithInvalidArgumentsShouldThrowExceptionTest() {
        User user = new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD, null,
                AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true);

        assertThrows(BadArgumentException.class, () ->
                user.setNameAndEmailAndLoginAndAddress(null, "", invalidLoginTooShort, null));
    }

    @Test
    public void setPasswordWithValidArgumentsShouldReturnSuccessTest() {
        User user = new User(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_LOGIN, VALID_PASSWORD, null,
                AddressHelper.getValidAddress(), UserTypeHelper.getValidUserType(), true);
        String newPassword = "novasenha";
        user.setPassword(newPassword);

        assertNotNull(user.getPassword());
        assertNotEquals(newPassword, user.getPassword());
        assertNotNull(user.getLastUpdateDate());
    }
}