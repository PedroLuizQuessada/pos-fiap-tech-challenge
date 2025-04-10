package com.example.tech_challenge.controller;

import com.example.tech_challenge.domain.user.UserResponse;
import com.example.tech_challenge.domain.user.request.NewUserRequest;
import com.example.tech_challenge.domain.user.request.UpdateUserRequest;
import com.example.tech_challenge.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public @ResponseBody UserResponse create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Validated NewUserRequest newUserRequest) {
        return userService.create(newUserRequest);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody @Validated UpdateUserRequest updateUserRequest) {
        userService.update(updateUserRequest);
        return ResponseEntity
                .ok().build();
    }
}
