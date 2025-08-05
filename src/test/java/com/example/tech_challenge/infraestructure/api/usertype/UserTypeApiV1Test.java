package com.example.tech_challenge.infraestructure.api.usertype;

import com.example.tech_challenge.controllers.UserTypeController;
import com.example.tech_challenge.dtos.requests.UserTypeRequest;
import com.example.tech_challenge.dtos.responses.UserTypeResponse;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.datasources.UserTypeDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeApiV1Test {

    @Mock
    UserDataSource mockUserDs;

    @Mock
    UserTypeDataSource mockUserTypeDs;

    @InjectMocks
    UserTypeApiV1 api;

    @Mock
    private UserTypeController spyController;

    private final UserTypeRequest req = new UserTypeRequest("novo tipo");
    private final UserTypeResponse resp = new UserTypeResponse(123L, "novo tipo");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Substituir o controller interno por um spy controlável
        ReflectionTestUtils.setField(api, "userTypeController", spyController);
    }

    @Test
    void create_returnsCreatedResponse() {
        when(spyController.createUserType(req)).thenReturn(resp);

        ResponseEntity<UserTypeResponse> response = api.create(req);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(spyController).createUserType(req);
    }

    @Test
    void update_returnsOkResponse() {
        Long id = 222L;
        when(spyController.updateUserTypeName(req, id)).thenReturn(resp);

        ResponseEntity<UserTypeResponse> response = api.update(req, id);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(spyController).updateUserTypeName(req, id);
    }

    @Test
    void findAll_returnsOkListResponse() {
        List<UserTypeResponse> list = List.of(resp);
        when(spyController.findAllUserTypes(5, 10)).thenReturn(list);

        ResponseEntity<List<UserTypeResponse>> response = api.findAll(5, 10);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1).containsExactly(resp);
        verify(spyController).findAllUserTypes(5, 10);
    }

    @Test
    void delete_returnsNoContent() {
        Long id = 333L;
        doNothing().when(spyController).deleteUserType(id);

        ResponseEntity<Void> response = api.delete(id);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(response.getBody()).isNull();
        verify(spyController).deleteUserType(id);
    }

    @Test
    void create_propagatesExceptionWhenControllerFails() {
        when(spyController.createUserType(req)).thenThrow(new IllegalArgumentException("bad"));

        assertThatThrownBy(() -> api.create(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bad");
        verify(spyController).createUserType(req);
    }

    @Test
    void update_propagatesExceptionWhenControllerFails() {
        Long id = 444L;
        when(spyController.updateUserTypeName(req, id))
                .thenThrow(new IllegalStateException("fails"));

        assertThatThrownBy(() -> api.update(req, id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("fails");
        verify(spyController).updateUserTypeName(req, id);
    }

    @Test
    void findAll_propagatesExceptionWhenControllerFails() {
        when(spyController.findAllUserTypes(0, 1))
                .thenThrow(new RuntimeException("nope"));

        assertThatThrownBy(() -> api.findAll(0, 1))
                .isInstanceOf(RuntimeException.class);
        verify(spyController).findAllUserTypes(0, 1);
    }

    @Test
    void delete_propagatesExceptionWhenControllerFails() {
        Long id = 555L;
        doThrow(new RuntimeException("err")).when(spyController).deleteUserType(id);

        assertThatThrownBy(() -> api.delete(id))
                .isInstanceOf(RuntimeException.class);
        verify(spyController).deleteUserType(id);
    }
}
