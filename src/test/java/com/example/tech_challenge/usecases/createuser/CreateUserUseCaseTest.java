package com.example.tech_challenge.usecases.createuser;

import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.dtos.requests.AddressRequest;
import com.example.tech_challenge.dtos.requests.CreateUserRequest;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.exceptions.AdminCreationNotAllowedException;
import com.example.tech_challenge.exceptions.EmailAlreadyInUseException;
import com.example.tech_challenge.exceptions.LoginAlreadyInUseException;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateUserUseCaseTest {

    private UserGateway userGateway;
    private UserTypeGateway userTypeGateway;
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        userTypeGateway = mock(UserTypeGateway.class);
        createUserUseCase = new CreateUserUseCase(userGateway, userTypeGateway);
    }

    @Test
    void shouldThrowAdminCreationNotAllowedException_whenUserTypeIsAdmin() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "Alice", "alice@example.com", "aliceLogin", null, "securePassword", UserTypeEnum.ADMIN.name()
        );

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(request))
                .isInstanceOf(AdminCreationNotAllowedException.class);
    }

    @Test
    void shouldThrowEmailAlreadyInUseException_whenEmailAlreadyExists() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "Bob", "bob@example.com", "bobLogin", null, "pass123", UserTypeEnum.OWNER.name()
        );

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);

        when(userTypeGateway.findUserTypeByName(UserTypeEnum.OWNER.name())).thenReturn(userType);
        when(userGateway.countByEmail("bob@example.com")).thenReturn(1L);

        try (MockedStatic<UserMapper> mocked = mockStatic(UserMapper.class)) {
            mocked.when(() -> UserMapper.toEntity(request, userType, true)).thenReturn(user);
            mocked.when(() -> UserMapper.toDto(user)).thenReturn(userDto);

            // Act & Assert
            assertThatThrownBy(() -> createUserUseCase.execute(request))
                    .isInstanceOf(EmailAlreadyInUseException.class);
        }
    }

    @Test
    void shouldThrowLoginAlreadyInUseException_whenLoginAlreadyExists() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "Carol", "carol@example.com", "carolLogin", null, "pass123", UserTypeEnum.OWNER.name()
        );

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);

        when(userTypeGateway.findUserTypeByName(UserTypeEnum.OWNER.name())).thenReturn(userType);
        when(userGateway.countByEmail("carol@example.com")).thenReturn(0L);
        when(userGateway.countByLogin("carolLogin")).thenReturn(1L);

        try (MockedStatic<UserMapper> mocked = mockStatic(UserMapper.class)) {
            mocked.when(() -> UserMapper.toEntity(request, userType, true)).thenReturn(user);
            mocked.when(() -> UserMapper.toDto(user)).thenReturn(userDto);

            // Act & Assert
            assertThatThrownBy(() -> createUserUseCase.execute(request))
                    .isInstanceOf(LoginAlreadyInUseException.class);
        }
    }

    @Test
    void shouldCreateUserSuccessfully_whenEmailAndLoginAreUnique_andUserTypeIsNotAdmin() {
        // Arrange
        AddressRequest addressRequest = mock(AddressRequest.class);
        CreateUserRequest request = new CreateUserRequest(
                "Dave", "dave@example.com", "daveLogin", addressRequest, "securePass", UserTypeEnum.OWNER.name()
        );

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);

        when(userTypeGateway.findUserTypeByName(UserTypeEnum.OWNER.name())).thenReturn(userType);
        when(userGateway.countByEmail("dave@example.com")).thenReturn(0L);
        when(userGateway.countByLogin("daveLogin")).thenReturn(0L);
        when(userGateway.createUser(userDto)).thenReturn(user);

        try (MockedStatic<UserMapper> mocked = mockStatic(UserMapper.class)) {
            mocked.when(() -> UserMapper.toEntity(request, userType, true)).thenReturn(user);
            mocked.when(() -> UserMapper.toDto(user)).thenReturn(userDto);

            // Act
            User result = createUserUseCase.execute(request);

            // Assert
            assertThat(result).isEqualTo(user);
        }
    }
}
