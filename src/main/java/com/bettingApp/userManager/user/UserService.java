package com.bettingApp.userManager.user;

import com.bettingApp.userManager.user.dto.UserRegistrationReq;
import com.bettingApp.userManager.user.dto.UserResponse;
import com.bettingApp.userManager.user.dto.UserUpdateReq;
import com.bettingApp.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponse register(UserRegistrationReq userReq);

    UserResponse me();

    UserResponse getById(Long userId);

    List<UserResponse> getUsers();

    UserResponse blockUser(Long id);

    UserResponse activateUser(Long id);

    UserResponse editUser(UserUpdateReq updateReq);

    ResponseEntity<ApiResponse> delete(Long id);
}
