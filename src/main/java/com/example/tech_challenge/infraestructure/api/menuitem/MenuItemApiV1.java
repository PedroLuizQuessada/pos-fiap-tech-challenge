package com.example.tech_challenge.infraestructure.api.menuitem;

import com.example.tech_challenge.controllers.MenuItemController;
import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.requests.CreateMenuItemRequest;
import com.example.tech_challenge.dtos.requests.DeleteMenuItemRequest;
import com.example.tech_challenge.dtos.requests.UpdateMenuItemRequest;
import com.example.tech_challenge.dtos.responses.MenuItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/cardapio-itens")
@Tag(name = "Restaurant API V1", description = "Versão 1 do controlador referente a itens do cardápio")
public class MenuItemApiV1 {

    private final MenuItemController menuItemController;

    public MenuItemApiV1(MenuItemDataSource menuItemDataSource, RestaurantDataSource restaurantDataSource, TokenDataSource tokenDataSource) {
        this.menuItemController = new MenuItemController(menuItemDataSource, restaurantDataSource, tokenDataSource);
    }

    @Operation(summary = "Cria um item do cardápio",
            description = "Requer autenticação e tipo de usuário 'OWNER'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Item do cardápio criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MenuItemResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para o item do cardápio a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'OWNER'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<MenuItemResponse> create(@RequestHeader(name = "Authorization") String token,
                                                   @RequestBody @Valid CreateMenuItemRequest menuItemRequest) {
        log.info("Creating menu item: {}", menuItemRequest.name());
        MenuItemResponse menuItemResponse = menuItemController.createMenuItem(menuItemRequest, token);
        log.info("Created menu item: {}", menuItemResponse.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuItemResponse);
    }

    @Operation(summary = "Owner consulta todos os itens do cardápio de um restaurante",
            description = "Requer autenticação e tipo de usuário 'OWNER'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Itens do cardápio do restaurante consultados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MenuItemResponse.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'OWNER'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<MenuItemResponse>> findByRestaurantAndOwnerLogin(@RequestHeader(name = "Authorization") String token,
                                                                                @PathVariable("id") Long id) {
        log.info("Finding menu items from restaurant {}", id);
        List<MenuItemResponse> menuItemResponseList = menuItemController.findMenuItensByRestaurantAndResquester(id, token);
        log.info("Found {} from restaurant {}", menuItemResponseList.size(), id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuItemResponseList);
    }

    @Operation(summary = "Admin consulta todos os itens do cardápio de um restaurante",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Itens do cardápio do restaurante consultados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MenuItemResponse.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/admin/{id}")
    public ResponseEntity<List<MenuItemResponse>> findByRestaurant(@PathVariable("id") Long id) {
        log.info("Admin finding menu items from restaurant {}", id);
        List<MenuItemResponse> menuItemResponseList = menuItemController.findMenuItensByRestaurant(id);
        log.info("Admin found {} from restaurant {}", menuItemResponseList.size(), id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuItemResponseList);
    }

    @Operation(summary = "Atualiza o item do cardápio",
            description = "Requer autenticação e tipo de usuário 'OWNER'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Item do cardápio atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MenuItemResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do item do cardápio a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'OWNER'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Item do cardápio a ser atualizado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping
    public ResponseEntity<MenuItemResponse> update(@RequestHeader(name = "Authorization") String token,
                                                   @RequestBody @Valid UpdateMenuItemRequest menuItemRequest) {
        log.info("Updating menu item: {}", menuItemRequest.oldName());
        MenuItemResponse menuItemResponse = menuItemController.updateMenuItemByRequester(menuItemRequest, token);
        log.info("Updated menu item: {}", menuItemResponse.name());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuItemResponse);
    }

    @Operation(summary = "Atualiza o item do cardápio",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Item do cardápio atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MenuItemResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do item do cardápio a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Item do cardápio a ser atualizado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/admin/{id}")
    public ResponseEntity<MenuItemResponse> adminUpdate(@RequestBody @Valid UpdateMenuItemRequest menuItemRequest,
                                                        @PathVariable("id") Long id) {
        log.info("Admin updating menu item: {}", id);
        MenuItemResponse menuItemResponse = menuItemController.updateMenuItem(menuItemRequest, id);
        log.info("Admin updated menu item: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuItemResponse);
    }

    @Operation(summary = "Apaga o item do cardápio do seu próprio restaurante",
            description = "Requer autenticação e tipo de usuário 'OWNER'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Item do cardápio apagado com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'OWNER'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Item do cardápio a ser apagado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestHeader(name = "Authorization") String token,
                                       @RequestBody @Valid DeleteMenuItemRequest deleteMenuItemRequest) {
        log.info("Deleting menu item: {}", deleteMenuItemRequest.name());
        menuItemController.deleteMenuItemByRequester(deleteMenuItemRequest, token);
        log.info("Deleted menu item: {}", deleteMenuItemRequest.name());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Admin apaga um item de cardápio",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Item de cardápio apagado com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Item do cardápio a ser apagado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> adminDelete(@PathVariable("id") Long id) {
        log.info("Admin deleting menu item: {}", id);
        menuItemController.deleteMenuItem(id);
        log.info("Admin deleted menu item: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }
}
