package com.example.tech_challenge.domain.interfaces;

public interface Persistence<T extends Entity> {
    T toEntity();
}
