package com.example.tech_challenge.entities;

import com.example.tech_challenge.exceptions.BadArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.example.tech_challenge.helper.TokenHelper.*;

public class TokenTest {

    private final String invalidTokenMessage = "O token deve possuir um valor.";
    private final String invalidLoginMessage = "O login do token deve possuir um valor.";

    @Test
    public void newTokenWithValidArgumentsShouldReturnSuccessTest() {
        Token token = new Token(VALID_TOKEN, VALID_LOGIN);

        assertNotNull(token);
        assertEquals(VALID_TOKEN, token.getToken());
        assertEquals(VALID_LOGIN, token.getLogin());
    }

    @Test
    public void newTokenWithNullTokenShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Token(null, VALID_LOGIN), invalidTokenMessage);
    }

    @Test
    public void newTokenWithEmptyTokenShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Token("", VALID_LOGIN), invalidTokenMessage);
    }

    @Test
    public void newTokenWithNullLoginShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Token(VALID_TOKEN, null), invalidLoginMessage);
    }

    @Test
    public void newTokenWithEmptyLoginShouldThrowExceptionTest() {
        assertThrows(BadArgumentException.class, () -> new Token(VALID_TOKEN, ""), invalidLoginMessage);
    }
}