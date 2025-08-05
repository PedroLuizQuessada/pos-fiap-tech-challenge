package com.example.tech_challenge.usecases;

import com.example.tech_challenge.entities.UserType;
import com.example.tech_challenge.gateways.UserTypeGateway;
import com.example.tech_challenge.helper.UserTypeHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindAllUserTypeUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private FindAllUserTypeUseCase findAllUserTypeUseCase;

    @Test
    public void executeWithValidArgumentsShouldReturnSuccess() {
        when(userTypeGateway.findAllUserTypes(anyInt(), anyInt())).thenReturn(List.of(UserTypeHelper.getValidUserType()));

        List<UserType> response = findAllUserTypeUseCase.execute(anyInt(), anyInt());

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserTypeHelper.getValidUserType().getId(), response.getFirst().getId());
    }
}
