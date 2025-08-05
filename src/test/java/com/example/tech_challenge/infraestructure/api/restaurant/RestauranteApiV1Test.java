package com.example.tech_challenge.infraestructure.api.restaurant;

import com.example.tech_challenge.controllers.RestaurantController;
import com.example.tech_challenge.dtos.requests.*;
import com.example.tech_challenge.dtos.responses.*;
import com.example.tech_challenge.datasources.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantApiV1Test {

    @Mock private UserDataSource mockUserDs;
    @Mock private RestaurantDataSource mockRestDs;
    @Mock private AddressDataSource mockAddrDs;
    @Mock private TokenDataSource mockTokenDs;
    @Mock private MenuItemDataSource mockItemDs;

    @InjectMocks
    private RestaurantApiV1 api;

    @Mock
    private RestaurantController controller;

    private final RestaurantRequest sampleReq =
            new RestaurantRequest("Resto", null, "Italiana", "08:00 18:00");
    private final UpdateRestaurantRequest sampleUpdReq =
            new UpdateRestaurantRequest("Old Resto", "New Resto", null, "Mexicana", "09:00 22:00");
    private final DeleteRestaurantRequest deleteReq =
            new DeleteRestaurantRequest("Resto");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(api, "restaurantController", controller);
    }

    @Test
    void create_owner_sucesso() {
        RestaurantResponse resp = new RestaurantResponse(1L, "Resto", null, "Italiana", "08:00 18:00", null);
        when(controller.createRestaurant(sampleReq, "Bearer tok")).thenReturn(resp);

        ResponseEntity<RestaurantResponse> response = api.create("Bearer tok", sampleReq);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).createRestaurant(sampleReq, "Bearer tok");
    }

    @Test
    void create_owner_exception_propaga() {
        when(controller.createRestaurant(sampleReq, "Bearer tok"))
                .thenThrow(new IllegalArgumentException("err"));

        assertThatThrownBy(() -> api.create("Bearer tok", sampleReq))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("err");
        verify(controller).createRestaurant(sampleReq, "Bearer tok");
    }

    @Test
    void findAll_usuario_sucesso() {
        RestaurantResponse resp = new RestaurantResponse(2L, "Resto2", null, "Japonesa", "10:00 20:00", null);
        when(controller.findRestaurants(3, 5)).thenReturn(List.of(resp));

        ResponseEntity<List<RestaurantResponse>> response = api.findAll(3, 5);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1).containsExactly(resp);
        verify(controller).findRestaurants(3, 5);
    }

    @Test
    void findAll_owner_sucesso() {
        RestaurantResponse resp = new RestaurantResponse(3L, "MeusRestos", null, "Brasileira", "11:00 23:00", null);

        // Stub com os parâmetros corretos:
        when(controller.findRestaurantsByOwnerByRequester(
                eq(0), eq(1), eq("Bearer tok")))
                .thenReturn(List.of(resp));

        ResponseEntity<List<RestaurantResponse>> response = api.findAll("Bearer tok", 0, 1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(resp);

        verify(controller).findRestaurantsByOwnerByRequester(0, 1, "Bearer tok");
    }

    @Test
    void adminFindAll_sucesso() {
        RestaurantResponse resp = new RestaurantResponse(4L, "Resto4", null, "Chinesa", "07:00 15:00", null);
        when(controller.findRestaurantsByOwner(1, 2, 2L)).thenReturn(List.of(resp));

        ResponseEntity<List<RestaurantResponse>> response = api.adminFindAll(2L, 1, 2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(resp);
        verify(controller).findRestaurantsByOwner(1, 2, 2L);
    }

    @Test
    void update_owner_sucesso() {
        RestaurantResponse resp = new RestaurantResponse(5L, "Updated", null, "Italiana", "09:00 19:00", null);
        when(controller.updateRestaurantByRequester(sampleUpdReq, "Bearer tok")).thenReturn(resp);

        ResponseEntity<RestaurantResponse> response = api.update("Bearer tok", sampleUpdReq);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).updateRestaurantByRequester(sampleUpdReq, "Bearer tok");
    }

    @Test
    void adminUpdate_sucesso() {
        RestaurantResponse resp = new RestaurantResponse(6L, "AdminUpdated", null, "Indiana", "10:00 18:00", null);
        when(controller.updateRestaurant(sampleReq, 10L)).thenReturn(resp);

        ResponseEntity<RestaurantResponse> response = api.adminUpdate(sampleReq, 10L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).updateRestaurant(sampleReq, 10L);
    }

    @Test
    void delete_owner_sucesso() {
        doNothing().when(controller).deleteRestaurantByRequester(deleteReq, "Bearer tok");

        ResponseEntity<Void> response = api.delete("Bearer tok", deleteReq);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(controller).deleteRestaurantByRequester(deleteReq, "Bearer tok");
    }

    @Test
    void adminDelete_sucesso() {
        doNothing().when(controller).deleteRestaurant(20L);

        ResponseEntity<Void> response = api.adminDelete(20L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(controller).deleteRestaurant(20L);
    }

    @Test
    void update_owner_exception_propaga() {
        when(controller.updateRestaurantByRequester(sampleUpdReq, "Bearer tok"))
                .thenThrow(new IllegalStateException("fail"));

        assertThatThrownBy(() -> api.update("Bearer tok", sampleUpdReq))
                .isInstanceOf(IllegalStateException.class);
    }
}
