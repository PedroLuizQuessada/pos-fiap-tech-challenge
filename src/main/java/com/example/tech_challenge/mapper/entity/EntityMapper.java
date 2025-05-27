package com.example.tech_challenge.mapper.entity;

import com.example.tech_challenge.domain.interfaces.Entity;
import com.example.tech_challenge.exception.ConstraintViolationException;

public interface EntityMapper<T extends Entity> {
    void validateEntity(T entity) throws ConstraintViolationException;
}
