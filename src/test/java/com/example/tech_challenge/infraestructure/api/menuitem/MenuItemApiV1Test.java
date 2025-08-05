package com.example.tech_challenge.infraestructure.api.menuitem;

import com.example.tech_challenge.controllers.MenuItemController;
import com.example.tech_challenge.dtos.requests.*;
import com.example.tech_challenge.dtos.responses.MenuItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemApiV1Test {

    @Mock private MenuItemController controller;
    private MenuItemApiV1 api;

    @BeforeEach
    void setup() {
        // cria instância com datasources nulos (não serão usados)
        api = new MenuItemApiV1(null, null, null);
        // injeta mock do controller no campo final usando ReflectionTestUtils
        ReflectionTestUtils.setField(api, "menuItemController", controller);
    }

    @Test
    void create_owner_successful() {
        CreateMenuItemRequest req = new CreateMenuItemRequest(
                "MyRestaurant", "Pizza", "Descrição", 12.5, true, "img.png"
        );
        MenuItemResponse resp = new MenuItemResponse(5L, null, "Pizza", "Descrição", 12.5, true, "img.png");
        when(controller.createMenuItem(req, "Bearer tok")).thenReturn(resp);

        ResponseEntity<MenuItemResponse> response = api.create("Bearer tok", req);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller, times(1)).createMenuItem(req, "Bearer tok");
    }

    @Test
    void create_owner_controllerThrowsRuntime_exception() {
        CreateMenuItemRequest req = new CreateMenuItemRequest(
                "Res", "X", "D", 1.1, false, "p.png"
        );
        doThrow(new IllegalArgumentException("bad")).when(controller).createMenuItem(req, "Bearer t");
        assertThatThrownBy(() -> api.create("Bearer t", req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bad");
    }

    @Test
    void findByRestaurant_name_header_successful() {
        MenuItemResponse item = new MenuItemResponse(1L, null, "Name", "Desc", 10.0, true, "pic.png");
        when(controller.findMenuItensByRestaurantName(2, 3, "ResName"))
                .thenReturn(Collections.singletonList(item));

        ResponseEntity<List<MenuItemResponse>> response =
                api.findByRestaurant("ResName", 2, 3);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).containsExactly(item);
        verify(controller).findMenuItensByRestaurantName(2, 3, "ResName");
    }

    @Test
    void findByRestaurant_adminId_successful() {
        MenuItemResponse item = new MenuItemResponse(2L, null, "Name2", "Desc2", 20.0, true, "pic2.png");
        when(controller.findMenuItensByRestaurant(4, 0, 99L))
                .thenReturn(Collections.singletonList(item));

        ResponseEntity<List<MenuItemResponse>> response =
                api.findByRestaurant(99L, 4, 0);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).containsExactly(item);
        verify(controller).findMenuItensByRestaurant(4, 0, 99L);
    }

    @Test
    void update_owner_successful() {
        UpdateMenuItemRequest req = new UpdateMenuItemRequest(
                "Res", "Old", "New", "Desc", 15.0, false, "pic.png"
        );
        MenuItemResponse resp = new MenuItemResponse(8L, null, "New", "Desc", 15.0, false, "pic.png");
        when(controller.updateMenuItemByRequester(req, "Bearer tok")).thenReturn(resp);

        ResponseEntity<MenuItemResponse> response = api.update("Bearer tok", req);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).updateMenuItemByRequester(req, "Bearer tok");
    }

    @Test
    void update_admin_successful() {
        AdminUpdateMenuItemRequest req = new AdminUpdateMenuItemRequest(
                "NewNm", "Desc", 7.77, true, "img.jpg"
        );
        MenuItemResponse resp = new MenuItemResponse(9L, null, "NewNm", "Desc", 7.77, true, "img.jpg");
        when(controller.updateMenuItem(req, 42L)).thenReturn(resp);

        ResponseEntity<MenuItemResponse> response = api.adminUpdate(req, 42L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(resp);
        verify(controller).updateMenuItem(req, 42L);
    }

    @Test
    void delete_owner_successful() {
        DeleteMenuItemRequest req = new DeleteMenuItemRequest("ResX", "NameY");
        doNothing().when(controller).deleteMenuItemByRequester(req, "Bearer tkn");

        ResponseEntity<Void> response = api.delete("Bearer tkn", req);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(controller).deleteMenuItemByRequester(req, "Bearer tkn");
    }

    @Test
    void delete_admin_successful() {
        doNothing().when(controller).deleteMenuItem(123L);

        ResponseEntity<Void> resp = api.adminDelete(123L);

        assertThat(resp.getStatusCodeValue()).isEqualTo(204);
        verify(controller).deleteMenuItem(123L);
    }
}
