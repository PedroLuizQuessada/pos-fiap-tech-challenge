package com.example.tech_challenge.infraestructure.api.restaurant;

import com.example.tech_challenge.controllers.RestaurantController;
import com.example.tech_challenge.datasources.*;
import com.example.tech_challenge.dtos.requests.DeleteRestaurantRequest;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.dtos.requests.UpdateRestaurantRequest;
import com.example.tech_challenge.dtos.responses.RestaurantResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(path = "/api/v1/restaurantes")
@Tag(name = "Restaurant API V1", description = "Versão 1 do controlador referente a restaurantes")
public class RestaurantApiV1 {

    private final RestaurantController restaurantController;

    public RestaurantApiV1(UserDataSource userDataSource, RestaurantDataSource restaurantDataSource, AddressDataSource addressDataSource,
                           TokenDataSource tokenDataSource, MenuItemDataSource menuItemDataSource) {
        this.restaurantController = new RestaurantController(userDataSource, restaurantDataSource, addressDataSource, menuItemDataSource, tokenDataSource);
    }

    @Operation(summary = "Cria um restaurante",
            description = "Requer autenticação e tipo de usuário 'OWNER'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Restaurante criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para o restaurante a ser criado",
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
    @PostMapping("/owner")
    public ResponseEntity<RestaurantResponse> create(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
                                                     @RequestBody @Valid RestaurantRequest restaurantRequest) {
        log.info("User creating restaurant: {}", restaurantRequest.name());
        RestaurantResponse restaurantResponse = restaurantController.createRestaurant(restaurantRequest, token);
        log.info("User created restaurant: {}", restaurantResponse.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurantResponse);
    }

    @Operation(summary = "Consulta restaurantes",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Restaurantes consultados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> findAll(@RequestParam("page") int page,
                                                            @RequestParam("size") int size) {
        log.info("User finding restaurants");
        List<RestaurantResponse> restaurantResponseList = restaurantController.findRestaurants(page, size);
        log.info("User found {} restaurants", restaurantResponseList.size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantResponseList);
    }

    @Operation(summary = "Owner consulta todos os seus restaurantes",
            description = "Requer autenticação e tipo de usuário 'OWNER'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Restaurantes consultados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'OWNER'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/owner")
    public ResponseEntity<List<RestaurantResponse>> findAll(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
                                                            @RequestParam("page") int page,
                                                            @RequestParam("size") int size) {
        log.info("Owner finding his restaurants");
        List<RestaurantResponse> restaurantResponseList = restaurantController.findRestaurantsByOwnerByRequester(page, size, token);
        log.info("Owner found {} restaurants", restaurantResponseList.size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantResponseList);
    }

    @Operation(summary = "Admin consulta todos os restaurantes de um usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Restaurantes consultados com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/admin/{owner-id}")
    public ResponseEntity<List<RestaurantResponse>> adminFindAll(@PathVariable("owner-id") Long ownerId,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size) {
        log.info("Admin finding all restaurants from user: {}", ownerId);
        List<RestaurantResponse> restaurantResponseList = restaurantController.findRestaurantsByOwner(page, size, ownerId);
        log.info("Admin found {} restaurants from user: {}", restaurantResponseList.size(), ownerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantResponseList);
    }

    @Operation(summary = "Atualiza o seu próprio restaurante",
            description = "Requer autenticação e tipo de usuário 'OWNER'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Restaurante atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do restaurante a ser atualizado",
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
                    description = "Restaurante a ser atualizado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/owner")
    public ResponseEntity<RestaurantResponse> update(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
                                                     @RequestBody @Valid UpdateRestaurantRequest updateAddressRequest) {
        log.info("User updating restaurant: {}", updateAddressRequest.oldName());
        RestaurantResponse restaurantResponse = restaurantController.updateRestaurantByRequester(updateAddressRequest, token);
        log.info("User updated restaurant: {}", restaurantResponse.name());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantResponse);
    }

    @Operation(summary = "Admin atualiza um restaurante",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Restaurante atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do restaurante a ser atualizado",
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
                    description = "Restaurante a ser atualizado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/admin/{id}")
    public ResponseEntity<RestaurantResponse> adminUpdate(@RequestBody @Valid RestaurantRequest restaurantRequest,
                                                          @PathVariable("id") Long id) {
        log.info("Admin updating restaurant: {}", id);
        RestaurantResponse restaurantResponse = restaurantController.updateRestaurant(restaurantRequest, id);
        log.info("Admin updated restaurant: {}", restaurantResponse.name());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantResponse);
    }

    @Operation(summary = "Apaga o seu próprio restaurante",
            description = "Requer autenticação e tipo de usuário 'OWNER'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Restaurante apagado com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'OWNER'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Restaurante a ser apagado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/owner")
    public ResponseEntity<Void> delete(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token,
                                       @RequestBody @Valid DeleteRestaurantRequest deleteRestaurantRequest) {
        log.info("User deleting restaurant: {}", deleteRestaurantRequest.name());
        restaurantController.deleteRestaurantByRequester(deleteRestaurantRequest, token);
        log.info("User deleted restaurant: {}", deleteRestaurantRequest.name());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Admin apaga um restaurante",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Restaurante apagado com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Restaurante a ser apagado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> adminDelete(@PathVariable("id") Long id) {
        log.info("Admin deleting restaurant: {}", id);
        restaurantController.deleteRestaurant(id);
        log.info("Admin deleted restaurant: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }
}
