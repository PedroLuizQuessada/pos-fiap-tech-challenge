package com.example.tech_challenge.usecases;

import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.exceptions.UserTypeNameAlreadyInUseException;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.helper.UserTypeHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserTypeUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private CreateUserTypeUseCase createUserTypeUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(userTypeGateway.countByName(anyString())).thenReturn(0L);
        when(userTypeGateway.createUserType(any())).thenReturn(UserTypeHelper.getValidUserType());

        UserTypeRequest request = new UserTypeRequest("Nome");

        UserType userType = createUserTypeUseCase.execute(request);

        assertNotNull(userType);
        assertEquals(userType.getId(), UserTypeHelper.getValidUserType().getId());
        assertEquals(userType.getName(), UserTypeHelper.getValidUserType().getName());
    }

    @Test
    public void executeWithDuplicateNameShouldThrowException() {
        when(userTypeGateway.countByName(anyString())).thenReturn(1L);

        UserTypeRequest request = new UserTypeRequest("Nome");

        assertThrows(UserTypeNameAlreadyInUseException.class, () -> createUserTypeUseCase.execute(request));
    }
}
