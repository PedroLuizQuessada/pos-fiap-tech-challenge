package com.example.tech_challenge.infraestructure.security;

import com.example.tech_challenge.exceptions.treateds.BadRequestException;
import com.example.tech_challenge.exceptions.treateds.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionHandlerTest {

    private CustomExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CustomExceptionHandler();
    }

    @Test
    void shouldHandleBadRequestException() {
        BadRequestException ex = new BadRequestException("Bad request occurred");

        ProblemDetail result = handler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
        assertEquals("Bad request occurred", result.getDetail());
    }

    @Test
    void shouldHandleNotFoundException() {
        NotFoundException ex = new NotFoundException("Resource not found");

        ProblemDetail result = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
        assertEquals("Resource not found", result.getDetail());
    }

    @Test
    void shouldHandleGenericRuntimeException() {
        RuntimeException ex = new RuntimeException("Unexpected error");

        ProblemDetail result = handler.handleRuntime(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatus());
        assertEquals("Unexpected error", result.getDetail());
    }

    @Test
    void shouldHandleNoResourceFoundException() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest("GET", "/non-existent");
        ServletWebRequest webRequest = new ServletWebRequest(servletRequest);

        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "/non-existent");

        ResponseEntity<Object> response = handler.handleNoResourceFoundException(ex, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertNotNull(body);
        assertEquals("Recurso não encontrado: /non-existent", body.getDetail());
    }

    @Test
    void shouldHandleHttpMessageNotReadable() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Malformed JSON", (HttpInputMessage) null);
        ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());

        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ProblemDetail body = (ProblemDetail) response.getBody();
        assertNotNull(body);
        assertTrue(Objects.requireNonNull(body.getDetail()).startsWith("Requisição mal realizada"));
    }
}
