package com.example.tech_challenge.infraestructure.api.restaurant;

import com.example.tech_challenge.controllers.RequesterController;
import com.example.tech_challenge.controllers.RestaurantController;
import com.example.tech_challenge.datasources.RequesterDataSource;
import com.example.tech_challenge.datasources.RestaurantDataSource;
import com.example.tech_challenge.datasources.TokenDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.requests.RestaurantRequest;
import com.example.tech_challenge.dtos.responses.RequesterResponse;
import com.example.tech_challenge.dtos.responses.RestaurantResponse;
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
@RequestMapping(path = "/api/v1/restaurantes")
@Tag(name = "Restaurant API V1", description = "Versão 1 do controlador referente a restaurantes")
public class RestaurantApiV1 {

    private final RestaurantController restaurantController;
    private final RequesterController requesterController;

    public RestaurantApiV1(UserDataSource userDataSource, RestaurantDataSource restaurantDataSource,
                           RequesterDataSource requesterDataSource, TokenDataSource tokenDataSource) {
        this.restaurantController = new RestaurantController(userDataSource, restaurantDataSource);
        this.requesterController = new RequesterController(requesterDataSource, tokenDataSource);
    }

    @Operation(summary = "Cria um tipo de restaurante",
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
    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestHeader(name = "Authorization", required = false) String token,
                                                   @RequestBody @Valid RestaurantRequest restaurantRequest) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} creating restaurant: {}", requesterResponse.login(), restaurantRequest.name());
        RestaurantResponse restaurantResponse = restaurantController.createRestaurant(restaurantRequest, requesterResponse.login());
        log.info("User {} created restaurant: {}", requesterResponse.login(), restaurantResponse.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurantResponse);
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
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> findAll(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestHeader(name = "Authorization", required = false) String token) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} finding all restaurants he owns", requesterResponse.login());
        List<RestaurantResponse> restaurantResponseList = restaurantController.findRestaurantsByOwner(requesterResponse.login());
        log.info("User {} found {} restaurants he owns", requesterResponse.login(), restaurantResponseList.size());

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
    public ResponseEntity<List<RestaurantResponse>> findAll(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestHeader(name = "Authorization", required = false) String token,
                                                            @PathVariable("owner-id") Long ownerId) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} finding all restaurants from user: {}", requesterResponse.login(), ownerId);
        List<RestaurantResponse> restaurantResponseList = restaurantController.findRestaurantsByOwner(ownerId);
        log.info("User {} found {} restaurants from user: {}", requesterResponse.login(), restaurantResponseList.size(), ownerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(restaurantResponseList);
    }

    private RequesterResponse getRequester(UserDetails userDetails, String token) {
        return (!Objects.isNull(userDetails)) ?
                requesterController.getRequester(userDetails.getAuthorities().stream().findFirst().isPresent() ?
                        String.valueOf(userDetails.getAuthorities().stream().findFirst().get()) : null, userDetails.getUsername()) :
                requesterController.getRequester(token);
    }
}
