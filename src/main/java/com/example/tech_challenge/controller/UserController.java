package com.example.tech_challenge.controller;

import com.example.tech_challenge.component.mapper.UserMapper;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.domain.user.dto.response.LoginUserResponse;
import com.example.tech_challenge.domain.user.dto.response.UserResponse;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserRequest;
import com.example.tech_challenge.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/home")
    public ResponseEntity<LoginUserResponse> home(@AuthenticationPrincipal UserDetails clientUserDetails) {
        log.info("Logged user: {}", clientUserDetails.getUsername());
        User user = userService.getUserByLogin(clientUserDetails.getUsername());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toLoginUserResponse(user));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@AuthenticationPrincipal UserDetails clientUserDetails,
                                               @RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Create user: {}", createUserRequest.getLogin());
        User user = userService.create(clientUserDetails, createUserRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toUserResponse(user));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@AuthenticationPrincipal UserDetails clientUserDetails,
                                       @RequestBody @Valid UpdateUserRequest updateUserRequest) {
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

    @PutMapping("/updatePassword")
    public ResponseEntity<Void> updatePassword(HttpSession httpSession, @AuthenticationPrincipal UserDetails clientUserDetails,
                                         @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Update Password user: {}", clientUserDetails.getUsername());
        userService.updatePassword(httpSession, clientUserDetails, updateUserPasswordRequest);
        return ResponseEntity
                .ok().build();
    }
}
