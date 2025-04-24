package com.example.tech_challenge.exception;

public class PathNotFoundException extends RuntimeException {
    public PathNotFoundException(String path) {
        super(String.format("URL %s não foi encontrada", path));
    }
}
