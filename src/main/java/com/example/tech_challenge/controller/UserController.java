package com.example.tech_challenge.controller;

import com.example.tech_challenge.domain.user.dto.response.LoginUserResponse;
import com.example.tech_challenge.domain.user.dto.response.UserResponse;
import com.example.tech_challenge.domain.user.dto.request.NewUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserRequest;
import com.example.tech_challenge.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public ResponseEntity<LoginUserResponse> home(@AuthenticationPrincipal UserDetails clientUserDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginUserResponse(clientUserDetails.getUsername())); //TODO criar mapper para construir responses
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@AuthenticationPrincipal UserDetails clientUserDetails, @RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Create user: {}", newUserRequest.getLogin());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(clientUserDetails, newUserRequest));
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@AuthenticationPrincipal UserDetails clientUserDetails, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        log.info("Update user: {}", updateUserRequest.getOldLogin());
        userService.update(clientUserDetails, updateUserRequest);
        return ResponseEntity
                .ok().build();
    }

    @DeleteMapping("/delete/{login}")
    public ResponseEntity<Void> delete(HttpSession httpSession, @AuthenticationPrincipal UserDetails clientUserDetails,
                                 @PathVariable("login") String login) {
        log.info("Delete user: {}", login);
        Integer responseStatus = userService.delete(httpSession, clientUserDetails, login);
        return ResponseEntity
                .status(responseStatus).build(); //204
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<Void> updatePassword(HttpSession httpSession, @AuthenticationPrincipal UserDetails clientUserDetails,
                                         @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Update Password user: {}", clientUserDetails.getUsername());
        userService.updatePassword(httpSession, clientUserDetails, updateUserPasswordRequest);
        return ResponseEntity
                .ok().build();
    }
}
