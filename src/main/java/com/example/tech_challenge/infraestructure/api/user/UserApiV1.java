package com.example.tech_challenge.infraestructure.api.user;

import com.example.tech_challenge.controllers.RequesterController;
import com.example.tech_challenge.controllers.UserController;
import com.example.tech_challenge.datasources.*;
import com.example.tech_challenge.dtos.requests.UpdateUserRequest;
import com.example.tech_challenge.dtos.responses.RequesterResponse;
import com.example.tech_challenge.dtos.responses.TokenResponse;
import com.example.tech_challenge.dtos.responses.UserResponse;
import com.example.tech_challenge.dtos.requests.CreateUserRequest;
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

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/usuarios")
@Tag(name = "User API V1", description = "Versão 1 do controlador referente a usuários")
public class UserApiV1 {

    private final UserController userController;
    private final RequesterController requesterController;

    public UserApiV1(UserDataSource userDataSource, AddressDataSource addressDataSource, TokenDataSource tokenDataSource,
                     UserTypeDataSource userTypeDataSource, RequesterDataSource requesterDataSource) {
        this.userController = new UserController(userDataSource, addressDataSource, tokenDataSource, userTypeDataSource);
        this.requesterController = new RequesterController(requesterDataSource, tokenDataSource);
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
                            schema = @Schema(implementation = ProblemDetail.class))),

            @ApiResponse(responseCode = "500",
                    description = "Falha ao gerar token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/gerar-token")
    public ResponseEntity<TokenResponse> generateToken(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestHeader(name = "Authorization", required = false) String token) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("User {} generating token", requesterResponse.login());
        TokenResponse response = userController.generateToken(requesterResponse.userType(), requesterResponse.login());
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
        UserResponse userResponse = userController.createUser(createUserRequest, false);
        log.info("Created user: {}", userResponse.login());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @Operation(summary = "Admin cria um usuário",
            description = "Requer autenticação e nível de autorização 'ADMIN'",
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
    public ResponseEntity<UserResponse> adminCreate(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestHeader(name = "Authorization", required = false) String token,
                                                    @RequestBody @Valid CreateUserRequest createUserRequest) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("Admin {} creating user: {}", requesterResponse.login(), createUserRequest.login());
        UserResponse userResponse = userController.createUser(createUserRequest, true);
        log.info("Admin {} created user: {}", requesterResponse.login(), userResponse.login());

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
    public ResponseEntity<UserResponse> update(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestHeader(name = "Authorization", required = false) String token,
                                               @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("Updating user: {}", requesterResponse.login());
        UserResponse userResponse = userController.updateUser(updateUserRequest, requesterResponse.login());
        log.info("Updated user: {}", userResponse.login());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @Operation(summary = "Admin atualiza um usuário",
            description = "Requer autenticação e nível de autorização 'ADMIN'",
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
    public ResponseEntity<UserResponse> adminUpdate(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestHeader(name = "Authorization", required = false) String token,
                                                    @RequestBody @Valid UpdateUserRequest updateUserRequest,
                                                    @PathVariable("id") Long id) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("Admin {} updating user: {}", requesterResponse.login(), id);
        UserResponse userResponse = userController.updateUser(updateUserRequest, id);
        log.info("Admin {} updated user: {}", requesterResponse.login(), id);

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
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestHeader(name = "Authorization", required = false) String token,
                                       HttpSession httpSession) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("Deleting user: {}", requesterResponse.login());
        userController.deleteUser(requesterResponse.login());
        httpSession.invalidate();
        log.info("Deleted user: {}", requesterResponse.login());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Admin apaga um usuário",
            description = "Requer autenticação e nível de autorização 'ADMIN'",
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
    public ResponseEntity<Void> adminDelete(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestHeader(name = "Authorization", required = false) String token,
                                            @PathVariable("id") Long id) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("Admin {} deleting user: {}", requesterResponse.login(), id);
        userController.deleteUser(id);
        log.info("Admin {} deleted user: {}", requesterResponse.login(), id);

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
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestHeader(name = "Authorization", required = false) String token,
                                               @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        RequesterResponse requesterResponse = getRequester(userDetails, token);
        log.info("Updating user password: {}", requesterResponse.login());
        userController.updatePasswordUser(updateUserPasswordRequest, requesterResponse.login());
        log.info("Updated user password: {}", requesterResponse.login());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private RequesterResponse getRequester(UserDetails userDetails, String token) {
        return (!Objects.isNull(userDetails)) ?
                requesterController.getRequester(userDetails.getAuthorities().stream().findFirst().isPresent() ?
                                String.valueOf(userDetails.getAuthorities().stream().findFirst().get()) : null, userDetails.getUsername()) :
                requesterController.getRequester(token);
    }
}
