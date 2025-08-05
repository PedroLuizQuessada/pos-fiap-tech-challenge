package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.UserTypeDataSource;
import com.example.tech_challenge.dtos.UserTypeDto;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.UserTypeNotFoundException;
import com.example.tech_challenge.mappers.UserTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeGatewayTest {

    private UserTypeDataSource userTypeDataSource;
    private UserTypeGateway userTypeGateway;

    @BeforeEach
    void setUp() {
        userTypeDataSource = mock(UserTypeDataSource.class);
        userTypeGateway = new UserTypeGateway(userTypeDataSource);
    }

    @Test
    void shouldCreateUserType() {
        UserTypeDto dto = new UserTypeDto(1L, "Admin");
        UserType entity = new UserType(1L, "Admin");

        when(userTypeDataSource.createUserType(dto)).thenReturn(dto);

        try (MockedStatic<UserTypeMapper> mocked = mockStatic(UserTypeMapper.class)) {
            mocked.when(() -> UserTypeMapper.toEntity(dto)).thenReturn(entity);

            UserType result = userTypeGateway.createUserType(dto);
            assertThat(result).isSameAs(entity);
        }
    }

    @Test
    void shouldUpdateUserType() {
        UserTypeDto dto = new UserTypeDto(2L, "Customer");
        UserType entity = new UserType(2L, "Customer");

        when(userTypeDataSource.updateUserType(dto)).thenReturn(dto);

        try (MockedStatic<UserTypeMapper> mocked = mockStatic(UserTypeMapper.class)) {
            mocked.when(() -> UserTypeMapper.toEntity(dto)).thenReturn(entity);

            UserType result = userTypeGateway.updateUserType(dto);
            assertThat(result).isSameAs(entity);
        }
    }

    @Test
    void shouldCountByName() {
        when(userTypeDataSource.countByName("Admin")).thenReturn(3L);
        Long count = userTypeGateway.countByName("Admin");
        assertThat(count).isEqualTo(3L);
    }

    @Test
    void shouldFindAllUserTypes() {
        UserTypeDto dto = new UserTypeDto(1L, "Admin");
        UserType entity = new UserType(1L, "Admin");

        when(userTypeDataSource.findAllUserTypes(0, 10)).thenReturn(List.of(dto));

        try (MockedStatic<UserTypeMapper> mocked = mockStatic(UserTypeMapper.class)) {
            mocked.when(() -> UserTypeMapper.toEntity(dto)).thenReturn(entity);

            List<UserType> result = userTypeGateway.findAllUserTypes(0, 10);
            assertThat(result).containsExactly(entity);
        }
    }

    @Test
    void shouldFindUserTypeByName() {
        UserTypeDto dto = new UserTypeDto(1L, "Manager");
        UserType entity = new UserType(1L, "Manager");

        when(userTypeDataSource.findUserTypeByName("Manager")).thenReturn(Optional.of(dto));

        try (MockedStatic<UserTypeMapper> mocked = mockStatic(UserTypeMapper.class)) {
            mocked.when(() -> UserTypeMapper.toEntity(dto)).thenReturn(entity);

            UserType result = userTypeGateway.findUserTypeByName("Manager");
            assertThat(result).isSameAs(entity);
        }
    }

    @Test
    void shouldThrowWhenUserTypeNameIsNull() {
        assertThatThrownBy(() -> userTypeGateway.findUserTypeByName(null))
                .isInstanceOf(UserTypeNotFoundException.class);
    }

    @Test
    void shouldThrowWhenUserTypeNotFoundByName() {
        when(userTypeDataSource.findUserTypeByName("Ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userTypeGateway.findUserTypeByName("Ghost"))
                .isInstanceOf(UserTypeNotFoundException.class);
    }

    @Test
    void shouldFindUserTypeById() {
        UserTypeDto dto = new UserTypeDto(5L, "Operator");
        UserType entity = new UserType(5L, "Operator");

        when(userTypeDataSource.findUserTypeById(5L)).thenReturn(Optional.of(dto));

        try (MockedStatic<UserTypeMapper> mocked = mockStatic(UserTypeMapper.class)) {
            mocked.when(() -> UserTypeMapper.toEntity(dto)).thenReturn(entity);

            UserType result = userTypeGateway.findUserTypeById(5L);
            assertThat(result).isSameAs(entity);
        }
    }

    @Test
    void shouldThrowWhenUserTypeIdIsNull() {
        assertThatThrownBy(() -> userTypeGateway.findUserTypeById(null))
                .isInstanceOf(UserTypeNotFoundException.class);
    }

    @Test
    void shouldThrowWhenUserTypeNotFoundById() {
        when(userTypeDataSource.findUserTypeById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userTypeGateway.findUserTypeById(99L))
                .isInstanceOf(UserTypeNotFoundException.class);
    }

    @Test
    void shouldDeleteUserType() {
        UserTypeDto dto = new UserTypeDto(10L, "Temp");

        userTypeGateway.delete(dto);

        verify(userTypeDataSource).deleteUserType(dto);
    }
}
