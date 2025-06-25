package com.example.tech_challenge.infraestructure.api.user;

import com.example.tech_challenge.controllers.UserController;
import com.example.tech_challenge.datasources.AddressDataSource;
import com.example.tech_challenge.datasources.UserDataSource;
import com.example.tech_challenge.dtos.request.UpdateUserRequest;
import com.example.tech_challenge.dtos.response.LoginUserResponse;
import com.example.tech_challenge.dtos.response.UserResponse;
import com.example.tech_challenge.dtos.request.CreateUserRequest;
import com.example.tech_challenge.dtos.request.UpdateUserPasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/usuarios")
@Tag(name = "User Controller V1", description = "Versão 1 do controlador referente a usuários")
public class UserControllerV1 {

    private final UserController userController;

    public UserControllerV1(UserDataSource userDataSource, AddressDataSource addressDataSource) {
        this.userController = new UserController(userDataSource, addressDataSource);
    }

    @Operation(summary = "Realiza login",
                security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                            description = "Usuário autenticado com sucesso",
                            content = @Content(mediaType = "application/json",
                                                schema = @Schema(implementation = LoginUserResponse.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/login")
    public ResponseEntity<LoginUserResponse> login(HttpServletRequest request) {
        log.info("Logging user...");
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        LoginUserResponse response = userController.login(authToken);
        log.info("Logged user: {}", response.message());

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
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Creating user: {}", createUserRequest.login());
        UserResponse userResponse = userController.createUser(createUserRequest, false);
        log.info("Created user: {}", userResponse.login());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @Operation(summary = "Admin cria um usuário",
            description = "Requer autenticação e nível de autorização 'ADMIN'",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/admin")
    public ResponseEntity<UserResponse> adminCreate(@RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Admin creating user: {}", createUserRequest.login());
        UserResponse userResponse = userController.createUser(createUserRequest, true);
        log.info("Admin created user: {}", userResponse.login());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @Operation(summary = "Atualiza o seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        log.info("Updating user...");
        UserResponse userResponse = userController.updateUser(updateUserRequest, 1L); //TODO
        log.info("Updated user: {}", userResponse.login());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @Operation(summary = "Admin atualiza um usuário",
            description = "Requer autenticação e nível de autorização 'ADMIN'",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser atualizado",
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
                                            @PathVariable("id") @NotNull Long id) {
        log.info("Admin updating user: {}", id);
        UserResponse userResponse = userController.updateUser(updateUserRequest, id);
        log.info("Admin updated user: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @Operation(summary = "Apaga o seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Usuário apagado com sucesso")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(HttpSession httpSession) {
        log.info("Deleting user...");
        userController.deleteUser(1L); //TODO
        httpSession.invalidate();
        log.info("Deleted user: {}", 1L); //TODO

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Admin apaga um usuário",
            description = "Requer autenticação e nível de autorização 'ADMIN'",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Usuário apagado com sucesso"),
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
    public ResponseEntity<Void> adminDelete(@PathVariable("id") @NotNull Long id) {
        log.info("Admin deleting user: {}", id);
        userController.deleteUser(id);
        log.info("Admin deleted user: {}", id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualiza a senha do seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Senha do usuário atualizada com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/senha")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Updating user password...");
        userController.updatePasswordUser(updateUserPasswordRequest, 1L); //TODO
        log.info("Updated user password: {}", 1L); //TODO

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
