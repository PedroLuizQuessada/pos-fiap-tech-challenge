package com.example.tech_challenge.datasources;

import com.example.tech_challenge.dtos.UserTypeDto;

import java.util.List;
import java.util.Optional;

public interface UserTypeDataSource {
    UserTypeDto createUserType(UserTypeDto userTypeDto);
    UserTypeDto updateUserType(UserTypeDto userTypeDto);
    Long countByName(String name);
    List<UserTypeDto> findAllUserTypes(int page, int size);
    Optional<UserTypeDto> findUserTypeByName(String name);
    Optional<UserTypeDto> findUserTypeById(Long id);
    void deleteUserType(UserTypeDto userTypeDto);
}
