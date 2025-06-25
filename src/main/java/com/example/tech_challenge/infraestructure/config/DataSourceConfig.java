package com.example.tech_challenge.infraestructure.config;

import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.infraestructure.persistence.jpa.repos.AddressRepositoryJpaImpl;
import com.example.tech_challenge.infraestructure.persistence.jpa.repos.UserRepositoryJpaImpl;
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

}
