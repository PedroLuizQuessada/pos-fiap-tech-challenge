package com.example.tech_challenge.mapper.response;

public interface ResponseMapper<T, U> {
    T map(U object);
}
