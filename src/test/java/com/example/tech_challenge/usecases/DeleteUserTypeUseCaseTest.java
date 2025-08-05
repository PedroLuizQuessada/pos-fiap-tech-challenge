package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.NativeUserTypeAlterationException;
import com.example.tech_challenge.exceptions.UserTypeWithUsersDeletionException;
import com.example.tech_challenge.gateways.UserGateway;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.helper.UserTypeHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUserTypeUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    private final Long nonNativeUserTypeId = 99L;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(userTypeGateway.findUserTypeById(anyLong())).thenReturn(UserTypeHelper.getValidUserType());
        when(userGateway.countByUserType(anyLong())).thenReturn(0L);
        doNothing().when(userTypeGateway).delete(any());

        deleteUserTypeUseCase.execute(nonNativeUserTypeId);

        verify(userTypeGateway, times(1)).delete(any());
    }

    @Test
    public void executeWithNativeUserTypeShouldThrowException() {
        when(userTypeGateway.findUserTypeById(anyLong())).thenReturn(UserTypeHelper.getValidUserType());

        assertThrows(NativeUserTypeAlterationException.class, () -> deleteUserTypeUseCase.execute(UserTypeHelper.VALID_ID));
    }

    @Test
    public void executeWithUserTypeWithUsersShouldThrowException() {
        when(userTypeGateway.findUserTypeById(anyLong())).thenReturn(new UserType(nonNativeUserTypeId, "nome"));
        when(userGateway.countByUserType(anyLong())).thenReturn(1L);

        assertThrows(UserTypeWithUsersDeletionException.class, () -> deleteUserTypeUseCase.execute(nonNativeUserTypeId));
    }
}
