package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import org.junit.jupiter.api.Test;
import static com.example.tech_challenge.helper.RequesterHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class RequesterTest {

    private final String invalidUserTypeMessage = "Falha ao detectar o tipo de usuário do requisitor, favor contactar o administrador.";
    private final String invalidLoginMessage = "Falha ao detectar o login do requisitor, favor contactar o administrador.";

    @Test
    public void newRequesterWithValidArgumentsShouldReturnSuccessTest() {
        Requester requester = new Requester(VALID_USER_TYPE,VALID_LOGIN);

        assertNotNull(requester);
        assertEquals(VALID_USER_TYPE, requester.getUserType());
        assertEquals(VALID_LOGIN, requester.getLogin());
    }

    @Test
    public void newRequesterWithNullUserTypeShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Requester(null, VALID_LOGIN), invalidUserTypeMessage);
    }

    @Test
    public void newRequesterWithEmptyUserTypeShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Requester("", VALID_LOGIN), invalidUserTypeMessage);
    }

    @Test
    public void newRequesterWithNullLoginShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Requester(VALID_USER_TYPE, null), invalidLoginMessage);
    }

    @Test
    public void newRequesterWithEmptyLoginShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Requester(VALID_USER_TYPE, ""), invalidLoginMessage);
    }
}
