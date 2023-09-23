package com.bettingApp.userManager.user.dto;

import com.bettingApp.userManager.user.Users;

public class UserMapper {
    public static UserResponse toUserResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().getName())
                .status(user.getUserStatus())
                .lastLogin(user.getLastLoggedIn())
                .enabled(user.isEnabled() ? "YES" : "NO")
                .updatedBy(user.getUpdatedBy())
                .registeredAt(user.getRegisteredAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

