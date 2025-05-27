package com.example.tech_challenge.config;

import com.example.tech_challenge.mapper.entity.AddressMapper;
import com.example.tech_challenge.mapper.entity.UserMapper;
import com.example.tech_challenge.repo.address.AddressJpaRepositoryImpl;
import com.example.tech_challenge.repo.address.AddressRepository;
import com.example.tech_challenge.repo.user.UserJpaRepositoryImpl;
import com.example.tech_challenge.repo.user.UserRepository;
import com.example.tech_challenge.service.address.AddressService;
import com.example.tech_challenge.service.address.AddressServiceImpl;
import com.example.tech_challenge.service.user.UserService;
import com.example.tech_challenge.service.user.UserServiceImpl;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository(), userMapper(), addressService());
    }

    @Bean
    public AddressService addressService() {
        return new AddressServiceImpl(addressRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new UserJpaRepositoryImpl();
    }

    @Bean
    public AddressRepository addressRepository() {
        return new AddressJpaRepositoryImpl();
    }

    @Bean
    public UserMapper userMapper() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return new UserMapper(factory.getValidator(), addressMapper());
    }

    @Bean
    public AddressMapper addressMapper() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return new AddressMapper(factory.getValidator());
    }

}
