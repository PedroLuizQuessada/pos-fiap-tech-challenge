package com.example.tech_challenge.usecases.createuser;

import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.dtos.requests.AdminCreateUserRequest;
import com.example.tech_challenge.dtos.requests.AddressRequest;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdminCreateUserUseCaseTest {

    private UserGateway userGateway;
    private UserTypeGateway userTypeGateway;
    private AdminCreateUserUseCase adminCreateUserUseCase;
    private CreateUserUseCase createUserUseCaseSpy;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        userTypeGateway = mock(UserTypeGateway.class);

        CreateUserUseCase realUseCase = new CreateUserUseCase(userGateway, userTypeGateway);
        createUserUseCaseSpy = spy(realUseCase);

        // Use reflection to inject the spy into AdminCreateUserUseCase
        adminCreateUserUseCase = new AdminCreateUserUseCase(userGateway, userTypeGateway) {
            {
                // override the final field through reflection or inline subclass for test
                try {
                    var field = AdminCreateUserUseCase.class.getDeclaredField("createUserUseCase");
                    field.setAccessible(true);
                    field.set(this, createUserUseCaseSpy);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    void shouldCreateUserSuccessfully_whenAllInputsAreValid() {
        // Arrange
        AddressRequest addressRequest = mock(AddressRequest.class);
        AdminCreateUserRequest request = new AdminCreateUserRequest(
                "Test User", "test@example.com", "testLogin", addressRequest, "123456", 2L
        );

        UserType userType = new UserType(2L, "OWNER");
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);

        when(userTypeGateway.findUserTypeById(2L)).thenReturn(userType);
        when(userGateway.countByEmail("test@example.com")).thenReturn(0L);
        when(userGateway.countByLogin("testLogin")).thenReturn(0L);
        when(userGateway.createUser(userDto)).thenReturn(user);

        try (MockedStatic<UserMapper> mocked = mockStatic(UserMapper.class)) {
            mocked.when(() -> UserMapper.toEntity(request, userType, true)).thenReturn(user);
            mocked.when(() -> UserMapper.toDto(user)).thenReturn(userDto);

            doReturn(user).when(createUserUseCaseSpy).createUser(request, user);

            // Act
            User result = adminCreateUserUseCase.execute(request);

            // Assert
            assertThat(result).isEqualTo(user);
            verify(userTypeGateway).findUserTypeById(2L);
            verify(createUserUseCaseSpy).createUser(request, user);
        }
    }
}
