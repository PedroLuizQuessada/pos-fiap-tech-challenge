package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.NativeUserTypeAlterationException;
import com.example.tech_challenge.exceptions.UserTypeNameAlreadyInUseException;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.helper.UserTypeHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUserTypeNameUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private UpdateUserTypeNameUseCase updateUserTypeNameUseCase;

    private final Long nonNativeUserTypeId = 99L;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(userTypeGateway.findUserTypeById(anyLong())).thenReturn(UserTypeHelper.getValidUserType());
        when(userTypeGateway.countByName(anyString())).thenReturn(0L);
        when(userTypeGateway.updateUserType(any())).thenReturn(UserTypeHelper.getValidUserType());

        UserType userType = updateUserTypeNameUseCase.execute(new UserTypeRequest("novo nome"), nonNativeUserTypeId);

        assertNotNull(userType);
        assertEquals(UserTypeHelper.getValidUserType().getId(), userType.getId());
        assertEquals(UserTypeHelper.getValidUserType().getName(), userType.getName());
    }

    @Test
    public void executeWithSameNameShouldReturnSuccess() {
        when(userTypeGateway.findUserTypeById(anyLong())).thenReturn(UserTypeHelper.getValidUserType());
        when(userTypeGateway.updateUserType(any())).thenReturn(UserTypeHelper.getValidUserType());

        UserType userType = updateUserTypeNameUseCase.execute(new UserTypeRequest(UserTypeHelper.VALID_NAME), nonNativeUserTypeId);

        assertNotNull(userType);
        assertEquals(UserTypeHelper.getValidUserType().getId(), userType.getId());
        assertEquals(UserTypeHelper.getValidUserType().getName(), userType.getName());
    }

    @Test
    public void executeWithNativeUserTypeShouldThrowException() {
        assertThrows(NativeUserTypeAlterationException.class, () -> updateUserTypeNameUseCase.execute(new UserTypeRequest("novo nome"), UserTypeHelper.VALID_ID));
    }

    @Test
    public void executeWithUserTypeWithUsersShouldThrowException() {
        when(userTypeGateway.findUserTypeById(anyLong())).thenReturn(UserTypeHelper.getValidUserType());
        when(userTypeGateway.countByName(anyString())).thenReturn(1L);

        assertThrows(UserTypeNameAlreadyInUseException.class, () -> updateUserTypeNameUseCase.execute(new UserTypeRequest("novo nome"), nonNativeUserTypeId));
    }

}
