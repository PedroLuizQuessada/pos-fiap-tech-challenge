package com.example.tech_challenge.controller.users;

import com.example.tech_challenge.mapper.response.LoginUserResponseMapper;
import com.example.tech_challenge.mapper.response.UserResponseMapper;
import com.example.tech_challenge.domain.user.entity.User;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserRequest;
import com.example.tech_challenge.domain.user.dto.response.LoginUserResponse;
import com.example.tech_challenge.domain.user.dto.response.UserResponse;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.service.login.LoginServiceImpl;
import com.example.tech_challenge.service.user.UserService;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/usuarios")
@AllArgsConstructor
@Tag(name = "User Controller V1", description = "Versão 1 do controlador referente a usuários")
public class UserControllerV1 {

    private final LoginServiceImpl loginService;
    private final UserService userService;

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
        User user = loginService.login(authToken, false);

        LoginUserResponseMapper responseMapper = new LoginUserResponseMapper();

        log.info("Logged user: {}", user.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMapper.map(user));
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

        User user = userService.create(createUserRequest, false);

        UserResponseMapper responseMapper = new UserResponseMapper();

        log.info("Created user: {}", user.getLogin());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMapper.map(user));
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
    public ResponseEntity<UserResponse> adminCreate(HttpServletRequest request,
                                                    @RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Admin creating user: {}", createUserRequest.login());

        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        loginService.login(authToken, true);

        User user = userService.create(createUserRequest, true);

        UserResponseMapper responseMapper = new UserResponseMapper();

        log.info("Admin created user: {}", user.getLogin());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMapper.map(user));
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
    public ResponseEntity<UserResponse> update(HttpServletRequest request,
                                       @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        log.info("Updating user...");

        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        User user = loginService.login(authToken, false);

        user = userService.update(updateUserRequest, user);

        UserResponseMapper responseMapper = new UserResponseMapper();

        log.info("Updated user: {}", user.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMapper.map(user));
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
    public ResponseEntity<UserResponse> adminUpdate(HttpServletRequest request,
                                            @RequestBody @Valid UpdateUserRequest updateUserRequest,
                                            @PathVariable("id") @NotNull Long id) {
        log.info("Admin updating user: {}", id);

        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        loginService.login(authToken, true);

        User user = userService.update(updateUserRequest, id);

        UserResponseMapper responseMapper = new UserResponseMapper();

        log.info("Admin updated user: {}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMapper.map(user));
    }

    @Operation(summary = "Apaga o seu próprio usuário",
            description = "Requer autenticação",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Usuário apagado com sucesso")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(HttpSession httpSession,
                                       HttpServletRequest request) {
        log.info("Deleting user...");

        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        User user = loginService.login(authToken, false);

        userService.delete(user.getId());
        httpSession.invalidate();
        log.info("Deleted user: {}", user.getId());

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
    public ResponseEntity<Void> adminDelete(HttpServletRequest request,
                                            @PathVariable("id") @NotNull Long id) {
        log.info("Admin deleting user: {}", id);

        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        loginService.login(authToken, true);

        userService.delete(id);
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
    public ResponseEntity<Void> updatePassword(HttpServletRequest request,
                                               @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Updating user password...");

        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        User user = loginService.login(authToken, false);

        userService.updatePassword(updateUserPasswordRequest, user);
        log.info("Updated user password: {}", user.getId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
