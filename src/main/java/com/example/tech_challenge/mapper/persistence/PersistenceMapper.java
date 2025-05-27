package com.example.tech_challenge.mapper.persistence;

import com.example.tech_challenge.domain.interfaces.Entity;
import com.example.tech_challenge.domain.interfaces.Persistence;

public interface PersistenceMapper<T extends Persistence<U>, U extends Entity> {
    T map(U entity);
}
