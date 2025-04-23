package com.example.tech_challenge.controller.users;

import com.example.tech_challenge.component.mapper.UserMapper;
import com.example.tech_challenge.domain.user.User;
import com.example.tech_challenge.domain.user.dto.request.UserRequest;
import com.example.tech_challenge.domain.user.dto.response.LoginUserResponse;
import com.example.tech_challenge.domain.user.dto.response.UserResponse;
import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
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
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserControllerV1 {

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
                                       @RequestBody @Valid UserRequest userRequest) {
        log.info("Update user: {}", clientUserDetails.getUsername());
        userService.update(userRequest, clientUserDetails.getUsername());
        return ResponseEntity
                .ok().build();
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Void> adminUpdate(@RequestBody @Valid UserRequest userRequest,
                                            @PathVariable("id") Long id) {
        log.info("Admin update user: {}", id);
        userService.update(userRequest, id);
        return ResponseEntity
                .ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(HttpSession httpSession, @AuthenticationPrincipal UserDetails clientUserDetails) {
        log.info("Delete user: {}", clientUserDetails.getUsername());
        userService.delete(clientUserDetails.getUsername());
        httpSession.invalidate();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> adminDelete(@PathVariable("id") Long id) {
        log.info("Admin delete user: {}", id);
        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Void> updatePassword(HttpSession httpSession, @AuthenticationPrincipal UserDetails clientUserDetails,
                                         @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        log.info("Update Password user: {}", clientUserDetails.getUsername());
        userService.updatePassword(httpSession, clientUserDetails.getUsername(), updateUserPasswordRequest);
        return ResponseEntity
                .ok().build();
    }
}
