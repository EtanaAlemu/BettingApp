package com.bettingApp.userManager.user.dto;

import com.bettingApp.utils.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String lastLogin;
    private String role;
    private Status status;
    private String enabled;
    private String registeredAt;
    private String updatedAt;
    private String updatedBy;
}
