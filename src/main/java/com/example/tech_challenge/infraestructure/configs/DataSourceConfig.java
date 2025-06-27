package com.example.tech_challenge.infraestructure.configs;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.infraestructure.persistence.jpa.repos.AddressRepositoryJpaImpl;
import com.example.tech_challenge.infraestructure.persistence.jpa.repos.UserRepositoryJpaImpl;
import com.example.tech_challenge.infraestructure.services.TokenServiceJwtImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    public UserDataSource userDataSource() {
        return new UserRepositoryJpaImpl();
    }

    @Bean
    public AddressDataSource addressDataSource() {
        return new AddressRepositoryJpaImpl();
    }

    @Bean
    public TokenDataSource tokenDataSource() {
        return new TokenServiceJwtImpl();
    }

}
