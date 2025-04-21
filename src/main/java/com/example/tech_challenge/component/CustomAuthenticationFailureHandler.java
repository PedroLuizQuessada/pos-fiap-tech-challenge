package com.example.tech_challenge.component;

import com.example.tech_challenge.controller.ExceptionHandlerController;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final Gson gson = new Gson();

    @Autowired
    private ExceptionHandlerController exceptionHandlerController;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        ProblemDetail problemDetail = exceptionHandlerController.handleAuthentication(new com.example.tech_challenge.exception.AuthenticationException());
        String problemDetailString = this.gson.toJson(problemDetail);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        out.print(problemDetailString);
        out.flush();
    }
}
