package com.example.tech_challenge.infraestructure.api.menuitem;

import com.example.tech_challenge.controllers.MenuItemController;
import com.example.tech_challenge.controllers.RequesterController;
import com.example.tech_challenge.datasources.MenuItemDataSource;
import com.example.tech_challenge.datasources.RequesterDataSource;
import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.dtos.requests.CreateMenuItemRequest;
import com.example.tech_challenge.dtos.requests.UpdateMenuItemRequest;
import com.example.tech_challenge.dtos.responses.MenuItemResponse;
import com.example.tech_challenge.dtos.responses.RequesterResponse;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/cardapio-itens")
@Tag(name = "Restaurant API V1", description = "Versão 1 do controlador referente a itens do cardápio")
public class MenuItemApiV1 {

    private final MenuItemController menuItemController;
    private final RequesterController requesterController;

    public MenuItemApiV1(MenuItemDataSource menuItemDataSource, RestaurantDataSource restaurantDataSource,
                         RequesterDataSource requesterDataSource, TokenDataSource tokenDataSource) {
        this.menuItemController = new MenuItemController(menuItemDataSource, restaurantDataSource);
        this.requesterController = new RequesterController(requesterDataSource, tokenDataSource);
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
    public ResponseEntity<MenuItemResponse> create(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestHeader(name = "Authorization", required = false) String token,
                                                   @RequestBody @Valid CreateMenuItemRequest menuItemRequest) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} creating menu item: {}", requesterResponse.login(), menuItemRequest.name());
        MenuItemResponse menuItemResponse = menuItemController.createMenuItem(menuItemRequest, requesterResponse.login());
        log.info("User {} created menu item: {}", requesterResponse.login(), menuItemResponse.name());

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
    public ResponseEntity<List<MenuItemResponse>> findByRestaurantAndOwnerLogin(@AuthenticationPrincipal UserDetails userDetails,
                                                                   @RequestHeader(name = "Authorization", required = false) String token,
                                                                   @PathVariable("id") Long id) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} finding menu items from restaurant {}", requesterResponse.login(), id);
        List<MenuItemResponse> menuItemResponseList = menuItemController.findMenuItem(id, requesterResponse.login());
        log.info("User {} found {} from restaurant {}", requesterResponse.login(), menuItemResponseList.size(), id);

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
    public ResponseEntity<List<MenuItemResponse>> findByRestaurant(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestHeader(name = "Authorization", required = false) String token,
                                                                   @PathVariable("id") Long id) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("Admin {} finding menu items from restaurant {}", requesterResponse.login(), id);
        List<MenuItemResponse> menuItemResponseList = menuItemController.findMenuItem(id);
        log.info("Admin {} found {} from restaurant {}", requesterResponse.login(), menuItemResponseList.size(), id);

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
    public ResponseEntity<MenuItemResponse> update(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestHeader(name = "Authorization", required = false) String token,
                                                   @RequestBody @Valid UpdateMenuItemRequest menuItemRequest) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} updating menu item: {}", requesterResponse.login(), menuItemRequest.oldName());
        MenuItemResponse menuItemResponse = menuItemController.updateMenuItem(menuItemRequest, requesterResponse.login());
        log.info("User {} updated menu item: {}", requesterResponse.login(), menuItemResponse.name());

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
    public ResponseEntity<MenuItemResponse> adminUpdate(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestHeader(name = "Authorization", required = false) String token,
                                                   @RequestBody @Valid UpdateMenuItemRequest menuItemRequest, @PathVariable("id") Long id) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("Admin {} updating menu item: {}", requesterResponse.login(), id);
        MenuItemResponse menuItemResponse = menuItemController.updateMenuItem(menuItemRequest, id);
        log.info("Admin {} updated menu item: {}", requesterResponse.login(), id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(menuItemResponse);
    }

    private RequesterResponse getRequester(UserDetails userDetails, String token) {
        return (!Objects.isNull(userDetails)) ?
                requesterController.getRequester(userDetails.getAuthorities().stream().findFirst().isPresent() ?
                        String.valueOf(userDetails.getAuthorities().stream().findFirst().get()) : null, userDetails.getUsername()) :
                requesterController.getRequester(token);
    }
}
