package com.example.tech_challenge.controller;

import com.example.tech_challenge.domain.user.UserResponse;
import com.example.tech_challenge.domain.user.request.NewUserRequest;
import com.example.tech_challenge.domain.user.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.request.UpdateUserRequest;
import com.example.tech_challenge.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public @ResponseBody UserResponse create(@AuthenticationPrincipal UserDetails clientUserDetails, @RequestBody @Validated NewUserRequest newUserRequest) {
        log.info("Create user: {}", newUserRequest.getLogin());
        return userService.create(clientUserDetails, newUserRequest);
    }

    @PostMapping("/update")
    public ResponseEntity update(@AuthenticationPrincipal UserDetails clientUserDetails, @RequestBody @Validated UpdateUserRequest updateUserRequest) {
        log.info("Update user: {}", updateUserRequest.getOldLogin());
        userService.update(clientUserDetails, updateUserRequest);
        return ResponseEntity
                .ok().build();
    }

    @DeleteMapping("/delete/{login}")
    public ResponseEntity delete(HttpSession httpSession, @AuthenticationPrincipal UserDetails clientUserDetails,
                                 @PathVariable("login") String login) {
        log.info("Delete user: {}", login);
        Integer responseStatus = userService.delete(httpSession, clientUserDetails, login);
        return ResponseEntity
                .status(responseStatus).build();
    }

    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(HttpSession httpSession, @AuthenticationPrincipal UserDetails clientUserDetails,
                                         @RequestBody @Validated UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Update Password user: {}", clientUserDetails.getUsername());
        userService.updatePassword(httpSession, clientUserDetails, updateUserPasswordRequest);
        return ResponseEntity
                .ok().build();
    }
}
