package com.bettingApp.userManager.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationReq {
    @NotBlank(message = "password is required")
    @Size(min = 6, max = 20, message = "password must be between 6 and 20 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
}