package com.example.tech_challenge.infraestructure.api.user;

import com.example.tech_challenge.controllers.UserController;
import com.example.tech_challenge.dtos.requests.*;
import com.example.tech_challenge.dtos.responses.*;
import com.example.tech_challenge.datasources.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserApiV1Test {

    @Mock UserDataSource mockUserDs;
    @Mock AddressDataSource mockAddrDs;
    @Mock TokenDataSource mockTokenDs;
    @Mock UserTypeDataSource mockUserTypeDs;
    @Mock RestaurantDataSource mockRestDs;
    @Mock MenuItemDataSource mockMenuItemDs;

    @InjectMocks
    private UserApiV1 api;

    @Mock
    private UserController controller;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        // Substitui o controller interno usando reflexão
        ReflectionTestUtils.setField(api, "userController", controller);
    }

    @Test
    void generateToken_successful() {
        // Arrange: mock de UserDetails com autoridade OWNER e nome "alice"
        UserDetails userDetails = mock(UserDetails.class);
        GrantedAuthority auth = () -> "OWNER";
        when(userDetails.getAuthorities()).thenReturn((Collection) Collections.singleton(auth));
        when(userDetails.getUsername()).thenReturn("alice");

        // O controller irá retornar esse TokenResponse
        TokenResponse returnToken = new TokenResponse("tok-abc", "alice");
        when(controller.generateToken(anyString(), eq("alice"))).thenReturn(returnToken);

        // Act: chamar o endpoint
        ResponseEntity<TokenResponse> entity = api.generateToken(userDetails);

        // Assert: status e corpo corretos
        assertThat(entity.getStatusCodeValue()).isEqualTo(200);
        assertThat(entity.getBody()).isEqualTo(returnToken);
        verify(controller).generateToken(anyString(), eq("alice"));
    }

    @Test
    void create_successful() {
        CreateUserRequest req = new CreateUserRequest("Eve", "eve@x.com", "eve123", null, "passw0rd", "OWNER");
        UserResponse resp = new UserResponse(55L, "Eve", "eve@x.com", "eve123", new Date(System.currentTimeMillis()), null, null);
        when(controller.createUser(req)).thenReturn(resp);

        ResponseEntity<UserResponse> response = api.create(req);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).createUser(req);
    }

    @Test
    void adminCreate_successful() {
        AdminCreateUserRequest req = new AdminCreateUserRequest("Frank", "f@x.com", "frank", null, "pAss12!", 2L);
        UserResponse resp = new UserResponse(66L, "Frank", "f@x.com", "frank", new Date(System.currentTimeMillis()), null, null);
        when(controller.adminCreateUser(req)).thenReturn(resp);

        ResponseEntity<UserResponse> response = api.adminCreate(req);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).adminCreateUser(req);
    }

    @Test
    void update_successful() {
        UpdateUserRequest req = new UpdateUserRequest("Zoe", "zoe@x.com", "zoeX", null);
        UserResponse resp = new UserResponse(77L, "Zoe", "zoe@x.com", "zoeX", new Date(System.currentTimeMillis()), null, null);
        String fakeToken = "Bearer tok";
        when(controller.updateUserByRequester(req, fakeToken)).thenReturn(resp);

        ResponseEntity<UserResponse> response = api.update(fakeToken, req);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).updateUserByRequester(req, fakeToken);
    }

    @Test
    void adminUpdate_successful() {
        UpdateUserRequest req = new UpdateUserRequest("Yannis", "y@x.com", "yannis", null);
        UserResponse resp = new UserResponse(88L, "Yannis", "y@x.com", "yannis", new Date(System.currentTimeMillis()), null, null);
        when(controller.updateUser(req, 99L)).thenReturn(resp);

        ResponseEntity<UserResponse> response = api.adminUpdate(req, 99L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).updateUser(req, 99L);
    }

    @Test
    void delete_self_invalidateSession() {
        String fakeToken = "Bearer t";
        HttpSession session = mock(HttpSession.class);
        doNothing().when(controller).deleteUserByRequester(fakeToken);

        ResponseEntity<Void> response = api.delete(fakeToken, session);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(controller).deleteUserByRequester(fakeToken);
        verify(session).invalidate();
    }

    @Test
    void adminDelete_successful() {
        doNothing().when(controller).deleteUser(123L);

        ResponseEntity<Void> response = api.adminDelete(123L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(controller).deleteUser(123L);
    }

    @Test
    void updatePassword_successful() {
        String fakeToken = "Bearer Z";
        UpdateUserPasswordRequest req = new UpdateUserPasswordRequest("newPass123");
        doNothing().when(controller).updateUserPassword(req, fakeToken);

        ResponseEntity<Void> response = api.updatePassword(fakeToken, req);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(controller).updateUserPassword(req, fakeToken);
    }
}
