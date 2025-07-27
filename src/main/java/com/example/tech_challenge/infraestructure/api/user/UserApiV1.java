package com.example.tech_challenge.infraestructure.api.user;

import com.example.tech_challenge.controllers.UserController;
import com.example.tech_challenge.datasources.*;
import com.example.tech_challenge.dtos.requests.CreateUserRequest;
import com.example.tech_challenge.dtos.requests.UpdateUserRequest;
import com.example.tech_challenge.dtos.responses.TokenResponse;
import com.example.tech_challenge.dtos.responses.UserResponse;
import com.example.tech_challenge.dtos.requests.AdminCreateUserRequest;
import com.example.tech_challenge.dtos.requests.UpdateUserPasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/usuarios")
@Tag(name = "User API V1", description = "Versão 1 do controlador referente a usuários")
public class UserApiV1 {

    private final UserController userController;

    public UserApiV1(UserDataSource userDataSource, AddressDataSource addressDataSource, TokenDataSource tokenDataSource,
                     UserTypeDataSource userTypeDataSource, RestaurantDataSource restaurantDataSource,
                     MenuItemDataSource menuItemDataSource) {
        this.userController = new UserController(userDataSource, addressDataSource, tokenDataSource, userTypeDataSource,
                restaurantDataSource, menuItemDataSource);
    }

    @Operation(summary = "Gera token de acesso",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                            description = "Token gerado com sucesso",
                            content = @Content(mediaType = "application/json",
                                                schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/gerar-token")
    public ResponseEntity<TokenResponse> generateToken(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("User {} generating token", userDetails.getUsername());
        TokenResponse response = userController.generateToken(String.valueOf(userDetails.getAuthorities().stream().findFirst().get()), userDetails.getUsername());
        log.info("User {} generated token", response.login());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @Operation(summary = "Cria um usuário",
            description = "Endpoint liberado para usuários não autenticados")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Tipo de usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Creating user: {}", createUserRequest.login());
        UserResponse userResponse = userController.createUser(createUserRequest);
        log.info("Created user: {}", userResponse.login());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @Operation(summary = "Admin cria um usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser criado",
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
                    description = "Tipo de usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/admin")
    public ResponseEntity<UserResponse> adminCreate(@RequestBody @Valid AdminCreateUserRequest createUserRequest) {
        log.info("Admin creating user: {}", createUserRequest.login());
        UserResponse userResponse = userController.adminCreateUser(createUserRequest);
        log.info("Admin created user: {}", userResponse.login());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @Operation(summary = "Atualiza o seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestHeader(name = "Authorization") String token,
                                               @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        log.info("Updating user");
        UserResponse userResponse = userController.updateUserByRequester(updateUserRequest, token);
        log.info("Updated user: {}", userResponse.login());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @Operation(summary = "Admin atualiza um usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser atualizado",
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
                    description = "Usuário a ser atualizado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/admin/{id}")
    public ResponseEntity<UserResponse> adminUpdate(@RequestBody @Valid UpdateUserRequest updateUserRequest,
                                                    @PathVariable("id") Long id) {
        log.info("Admin updating user: {}", id);
        UserResponse userResponse = userController.updateUser(updateUserRequest, id);
        log.info("Admin updated user: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @Operation(summary = "Apaga o seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Usuário apagado com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestHeader(name = "Authorization") String token, HttpSession httpSession) {
        log.info("Deleting user");
        userController.deleteUserByRequester(token);
        httpSession.invalidate();
        log.info("Deleted user");

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Admin apaga um usuário",
            description = "Requer autenticação e tipo de usuário 'ADMIN'",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Usuário apagado com sucesso"),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Usuário a ser apagado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> adminDelete(@PathVariable("id") Long id) {
        log.info("Admin deleting user: {}", id);
        userController.deleteUser(id);
        log.info("Admin deleted user: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualiza a senha do seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Senha do usuário atualizada com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/senha")
    public ResponseEntity<Void> updatePassword(@RequestHeader(name = "Authorization") String token,
                                               @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Updating user password");
        userController.updateUserPassword(updateUserPasswordRequest, token);
        log.info("Updated user password");

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
