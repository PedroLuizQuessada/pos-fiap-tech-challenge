package com.example.tech_challenge.controller.users;

import com.example.tech_challenge.component.mapper.UserMapper;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.domain.user.dto.request.UserRequest;
import com.example.tech_challenge.domain.user.dto.response.LoginUserResponse;
import com.example.tech_challenge.domain.user.dto.response.UserResponse;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.service.LoginService;
import com.example.tech_challenge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserControllerV1 {

    private final LoginService loginService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Realiza login no sistema",
                security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/login")
    public ResponseEntity<LoginUserResponse> login(HttpServletRequest request) {
        log.info("Logging user...");
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        User user = loginService.login(authToken, false);
        log.info("Logged user: {}", user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toLoginUserResponse(user));
    }

    @Operation(summary = "Cria um usuário")
    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Create user: {}", createUserRequest.getLogin());
        User user = userService.create(createUserRequest, false);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toUserResponse(user));
    }

    @Operation(summary = "Admin cria um usuário",
            security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/admin/create")
    public ResponseEntity<UserResponse> adminCreate(HttpServletRequest request,
                                                    @RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Admin create user: {}", createUserRequest.getLogin());
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        loginService.login(authToken, true);
        User user = userService.create(createUserRequest, true);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toUserResponse(user));
    }

    @Operation(summary = "Atualiza o seu próprio usuário",
            security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping("/update")
    public ResponseEntity<Void> update(HttpServletRequest request,
                                       @RequestBody @Valid UserRequest userRequest) {
        log.info("Updating user...");
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        User user = loginService.login(authToken, false);
        userService.update(userRequest, user);
        log.info("Updated user: {}", user.getId());
        return ResponseEntity
                .ok().build();
    }

    @Operation(summary = "Admin atualiza um usuário",
            security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Void> adminUpdate(HttpServletRequest request,
                                            @RequestBody @Valid UserRequest userRequest,
                                            @PathVariable("id") @NotNull Long id) {
        log.info("Admin update user: {}", id);
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        loginService.login(authToken, true);
        userService.update(userRequest, id);
        return ResponseEntity
                .ok().build();
    }

    @Operation(summary = "Apaga o seu próprio usuário",
            security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping("/delete")
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
            security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> adminDelete(HttpServletRequest request,
                                            @PathVariable("id") @NotNull Long id) {
        log.info("Admin delete user: {}", id);
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        loginService.login(authToken, true);
        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualiza a senha do seu próprio usuário",
            security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping("/updatePassword")
    public ResponseEntity<Void> updatePassword(HttpServletRequest request,
                                               @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Updating password user...");
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        User user = loginService.login(authToken, false);
        userService.updatePassword(user.getId(), updateUserPasswordRequest);
        log.info("Password updated user: {}", user.getId());
        return ResponseEntity
                .ok().build();
    }
}
