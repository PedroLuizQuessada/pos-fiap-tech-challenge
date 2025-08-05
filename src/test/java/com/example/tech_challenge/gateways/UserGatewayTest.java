package com.example.tech_challenge.gateways;

import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.exceptions.UserNotFoundException;
import com.example.tech_challenge.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserGatewayTest {

    private UserDataSource userDataSource;
    private UserGateway userGateway;

    @BeforeEach
    void setUp() {
        userDataSource = mock(UserDataSource.class);
        userGateway = new UserGateway(userDataSource);
    }

    @Test
    void shouldFindUserByLogin() {
        UserDto dto = new UserDto(1L, "John", "john@example.com", "john", "password", new Date(System.currentTimeMillis()), null, null);
        User user = mock(User.class);

        when(userDataSource.findUserByLogin("john")).thenReturn(Optional.of(dto));

        try (MockedStatic<UserMapper> mocked = mockStatic(UserMapper.class)) {
            mocked.when(() -> UserMapper.toEntity(dto, false)).thenReturn(user);

            User result = userGateway.findUserByLogin("john");

            assertThat(result).isSameAs(user);
        }
    }

    @Test
    void shouldThrowWhenUserNotFoundByLogin() {
        when(userDataSource.findUserByLogin("nonexistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userGateway.findUserByLogin("nonexistent"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldCountByEmail() {
        when(userDataSource.countByEmail("user@example.com")).thenReturn(10L);
        Long result = userGateway.countByEmail("user@example.com");
        assertThat(result).isEqualTo(10L);
    }

    @Test
    void shouldCountByLogin() {
        when(userDataSource.countByLogin("user")).thenReturn(3L);
        Long result = userGateway.countByLogin("user");
        assertThat(result).isEqualTo(3L);
    }

    @Test
    void shouldCreateUser() {
        UserDto dto = new UserDto(1L, "Ana", "ana@example.com", "ana", "pass", new Date(System.currentTimeMillis()), null, null);
        User user = mock(User.class);

        when(userDataSource.createUser(dto)).thenReturn(dto);

        try (MockedStatic<UserMapper> mocked = mockStatic(UserMapper.class)) {
            mocked.when(() -> UserMapper.toEntity(dto, false)).thenReturn(user);

            User result = userGateway.createUser(dto);
            assertThat(result).isSameAs(user);
        }
    }

    @Test
    void shouldFindUserById() {
        UserDto dto = new UserDto(2L, "Eva", "eva@example.com", "eva", "123456", new Date(System.currentTimeMillis()), null, null);
        User user = mock(User.class);

        when(userDataSource.findUserById(2L)).thenReturn(Optional.of(dto));

        try (MockedStatic<UserMapper> mocked = mockStatic(UserMapper.class)) {
            mocked.when(() -> UserMapper.toEntity(dto, false)).thenReturn(user);

            User result = userGateway.findUserById(2L);
            assertThat(result).isSameAs(user);
        }
    }

    @Test
    void shouldThrowWhenUserIdIsNull() {
        assertThatThrownBy(() -> userGateway.findUserById(null))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldThrowWhenUserNotFoundById() {
        when(userDataSource.findUserById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userGateway.findUserById(999L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldUpdateUser() {
        UserDto dto = new UserDto(3L, "Leo", "leo@example.com", "leo", "mypassword", new Date(System.currentTimeMillis()), null, null);
        User user = mock(User.class);

        when(userDataSource.updateUser(dto)).thenReturn(dto);

        try (MockedStatic<UserMapper> mocked = mockStatic(UserMapper.class)) {
            mocked.when(() -> UserMapper.toEntity(dto, false)).thenReturn(user);

            User result = userGateway.updateUser(dto);
            assertThat(result).isSameAs(user);
        }
    }

    @Test
    void shouldDeleteUser() {
        UserDto dto = mock(UserDto.class);

        userGateway.deleteUser(dto);

        verify(userDataSource).deleteUser(dto);
    }

    @Test
    void shouldCountByUserType() {
        when(userDataSource.countByUserType(99L)).thenReturn(42L);
        Long result = userGateway.countByUserType(99L);
        assertThat(result).isEqualTo(42L);
    }
}
