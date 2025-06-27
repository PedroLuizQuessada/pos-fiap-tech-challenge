package com.example.tech_challenge.infraestructure.api;

import com.example.tech_challenge.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { EmailAlreadyInUseException.class, LoginAlreadyInUseException.class,
            AdminCreationNotAllowedException.class, BadArgumentException.class })
    public ProblemDetail handleBadRequest(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ProblemDetail handleUserNotFound(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = { TokenException.class, RuntimeException.class })
    public ProblemDetail handleRuntime(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, String.format("Recurso não encontrado: %s", ((ServletWebRequest) request).getRequest().getRequestURI())));
    }
}
