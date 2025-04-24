package com.example.tech_challenge.component.security;

import com.example.tech_challenge.controller.ExceptionHandlerController;
import com.example.tech_challenge.exception.AuthenticationException;
import com.example.tech_challenge.exception.AuthorityException;
import com.example.tech_challenge.exception.PathNotFoundException;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Gson gson = new Gson();

    private List<String> endpoints;

    @Autowired
    private ExceptionHandlerController exceptionHandlerController;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException  {

        ProblemDetail problemDetail;
        PrintWriter out = response.getWriter();
        String endpointRequested = getEndpoint(request.getRequestURL().toString());

        problemDetail = checkForAuthorizationException(accessDeniedException, endpointRequested);
        if (Objects.isNull(problemDetail))
            problemDetail = checkForPathNotFoundException(endpointRequested);
        if (Objects.isNull(problemDetail))
            problemDetail = exceptionHandlerController.handleAuthentication(new AuthenticationException());

        String problemDetailString = this.gson.toJson(problemDetail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(problemDetail.getStatus());
        out.print(problemDetailString);
        out.flush();
    }

    private String getEndpoint(String fullUrl) {
        return fullUrl.split(contextPath)[1];
    }

    private ProblemDetail checkForAuthorizationException(AccessDeniedException accessDeniedException, String endpointRequested) {
        try {
            ((AuthorityAuthorizationDecision) ((AuthorizationDeniedException) accessDeniedException).getAuthorizationResult()).getAuthorities();
            ProblemDetail problemDetail = exceptionHandlerController.handleAuthority(new AuthorityException());
            problemDetail.setInstance(URI.create(endpointRequested));
            return problemDetail;
        }
        catch (ClassCastException exception) {
            return null;
        }
    }

    private ProblemDetail checkForPathNotFoundException(String endpointRequested) {
        for (String endpoint : endpoints) {
            endpoint = removePathParam(endpoint);
            if (endpointRequested.contains(endpoint)) {
                ProblemDetail problemDetail = exceptionHandlerController.handlePathNotFound(new PathNotFoundException(endpointRequested));
                problemDetail.setInstance(URI.create(endpointRequested));
                return problemDetail;
            }
        }
        return null;
    }

    private String removePathParam(String fullEndpoint) {
        if (fullEndpoint.contains("{")) {
            return fullEndpoint.substring(0, fullEndpoint.indexOf("{"));
        }
        return fullEndpoint;
    }

    public void setEndpoints(List<List<String>> endpointsList) {
        endpoints = new ArrayList<>();
        for (List<String> endpoints : endpointsList) {
            this.endpoints.addAll(endpoints);
        }
    }
}
