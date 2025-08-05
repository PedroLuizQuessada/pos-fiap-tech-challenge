package com.example.tech_challenge.usecases.deleteuser;

import com.example.tech_challenge.dtos.AddressDto;
import com.example.tech_challenge.dtos.UserDto;
import com.example.tech_challenge.entities.Address;
import com.example.tech_challenge.entities.Requester;
import com.example.tech_challenge.entities.User;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.enums.UserTypeEnum;
import com.example.tech_challenge.gateways.*;
import com.example.tech_challenge.mappers.AddressMapper;
import com.example.tech_challenge.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;

import static org.mockito.Mockito.*;

class DeleteUserByRequesterUseCaseTest {

    private UserGateway userGateway;
    private AddressGateway addressGateway;
    private RestaurantGateway restaurantGateway;
    private MenuItemGateway menuItemGateway;
    private TokenGateway tokenGateway;

    @BeforeEach
    void setup() {
        userGateway = mock(UserGateway.class);
        addressGateway = mock(AddressGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        menuItemGateway = mock(MenuItemGateway.class);
        tokenGateway = mock(TokenGateway.class);

    }

    @Test
    void shouldDeleteUserByToken() {
        // Arrange
        String token = "valid-token";
        String login = "joao";
        String email = "joao@example.com";
        Long userId = 77L;

        Address address = new Address(1L, "SP", "São Paulo", "Rua A", "123", "00000-000", "Casa");
        AddressDto addressDto = AddressMapper.toDto(address);

        UserType userType = new UserType(UserTypeEnum.OWNER.getId(), UserTypeEnum.OWNER.name());
        User user = new User(userId, "João", email, login, "secret", new Date(Date.from(Instant.now()).getTime()), address, userType, false);
        UserDto userDto = UserMapper.toDto(user);

        Requester requester = new Requester(userType.getName(), login);

        when(tokenGateway.getRequester(token)).thenReturn(requester);
        when(userGateway.findUserByLogin(login)).thenReturn(user);

        // Spy on the internal DeleteUserUseCase for validation
//        DeleteUserUseCase deleteUserUseCase = spy(new DeleteUserUseCase(userGateway, addressGateway, restaurantGateway, menuItemGateway));
        DeleteUserByRequesterUseCase useCase = new DeleteUserByRequesterUseCase(
                userGateway, addressGateway, restaurantGateway, menuItemGateway, tokenGateway
        );

        // Inject spy into the internal field using reflection or constructor (if changed)
        // Instead, just call the real use case and verify gateway call
        doNothing().when(userGateway).deleteUser(userDto);

        // Act
        useCase.execute(token);

        // Assert
        verify(tokenGateway).getRequester(token);
        verify(userGateway).findUserByLogin(login);
        verify(userGateway).deleteUser(userDto);
        verify(addressGateway).delete(addressDto);
    }
}
