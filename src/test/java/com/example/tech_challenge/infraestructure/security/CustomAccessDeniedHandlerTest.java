package com.example.tech_challenge.infraestructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomAccessDeniedHandlerTest {

    private CustomAccessDeniedHandler handler;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AccessDeniedException exception;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        handler = new CustomAccessDeniedHandler();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        exception = new AccessDeniedException("Access denied");
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturn403WithProblemDetailJson() throws Exception {
        // Arrange
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn("/secure-endpoint");
        when(response.getWriter()).thenReturn(printWriter);

        // Act
        handler.handle(request, response, exception);

        // Assert
        verify(response).setStatus(HttpStatus.FORBIDDEN.value());
        verify(response).setContentType("application/problem+json");

        ProblemDetail expected = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Recurso não permitido.");
        expected.setInstance(URI.create("/secure-endpoint"));
        String expectedJson = objectMapper.writeValueAsString(expected);

        printWriter.flush(); // Ensure writer writes to stringWriter
        String actualJson = stringWriter.toString();

        assertEquals(expectedJson, actualJson);
    }
}
