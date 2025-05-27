package com.example.tech_challenge.mapper.persistence;

import com.example.tech_challenge.domain.address.persistence.jpa.AddressJpa;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.persistence.jpa.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserJpaMapper implements PersistenceMapper<UserJpa, User> {

    @Autowired
    private AddressJpaMapper addressJpaMapper;

    @Override
    public UserJpa map(User entity) {
        AddressJpa addressJpa = Objects.isNull(entity.getAddress()) ? null : addressJpaMapper.map(entity.getAddress());
        UserJpa userJpa = new UserJpa(entity.getId(), entity.getName(), entity.getEmail(), entity.getLogin(),
                entity.getEncodedPassword(), entity.getLastUpdateDate(), addressJpa, entity.getAuthority());
        if (!Objects.isNull(userJpa.getAddressJpa()))
            userJpa.getAddressJpa().setUserJpa(userJpa);
        return userJpa;
    }
}
