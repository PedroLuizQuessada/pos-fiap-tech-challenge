package com.example.tech_challenge.controller;

import com.example.tech_challenge.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { EmailAlreadyInUseException.class, LoginAlreadyInUseException.class })
    public ProblemDetail handleEmailOrLoginAlreadyInUse(RuntimeException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = { UnauthorizedActionException.class })
    public ProblemDetail handleUnauthorizedAction(UnauthorizedActionException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(value = { AuthenticationException.class })
    public ProblemDetail handleAuthentication(AuthenticationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        problemDetail.setInstance(URI.create("/tech-challenge/login"));
        return problemDetail;
    }

    @ExceptionHandler(value = { PathNotFoundException.class })
    public ProblemDetail handlePathNotFound(PathNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle(HttpStatus.NOT_FOUND.getReasonPhrase());
        return problemDetail;
    }

    @ExceptionHandler(value = { AuthorityException.class })
    public ProblemDetail handleAuthority(AuthorityException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle(HttpStatus.FORBIDDEN.getReasonPhrase());
        return problemDetail;
    }

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ProblemDetail handleUserNotFound(UserNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = { RuntimeException.class })
    public ProblemDetail handleRuntime(RuntimeException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder message = new StringBuilder();
        message.append("Parâmetro deve ser informado na URL: ");
        for(Object msg : Objects.requireNonNull(ex.getDetailMessageArguments())) {
            message.append(msg);
            break;
        }

        return ResponseEntity
                .badRequest()
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.valueOf(message)));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder message = new StringBuilder();
        for(Object msg : ex.getDetailMessageArguments()) {
            message.append(msg);
        }

        return ResponseEntity
                .badRequest()
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.valueOf(message)));
    }

}
