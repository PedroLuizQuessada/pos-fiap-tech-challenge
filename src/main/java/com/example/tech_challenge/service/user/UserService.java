package com.example.tech_challenge.service.user;

import com.example.tech_challenge.domain.user.dto.request.CreateUserRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserPasswordRequest;
import com.example.tech_challenge.domain.user.dto.request.UpdateUserRequest;
import com.example.tech_challenge.domain.user.entity.User;

public interface UserService {
    User create(CreateUserRequest createUserRequest, boolean allowAdmin);
    void update(UpdateUserRequest updateUserRequest, Long id);
    void update(UpdateUserRequest updateUserRequest, User updateUserOld);
    void delete(Long id);
    void updatePassword(UpdateUserPasswordRequest updateUserPasswordRequest, User updatePasswordUser);
}
