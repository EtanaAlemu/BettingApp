package com.bettingApp.userManager.user;

import com.bettingApp.exceptions.customExceptions.BadRequestException;
import com.bettingApp.exceptions.customExceptions.ResourceNotFoundException;
import com.bettingApp.userManager.role.Role;
import com.bettingApp.userManager.user.dto.UserMapper;
import com.bettingApp.userManager.user.dto.UserRegistrationReq;
import com.bettingApp.userManager.user.dto.UserResponse;
import com.bettingApp.utils.CurrentLoggedInUser;
import com.bettingApp.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentLoggedInUser currentLoggedInUser;
    private final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(\\+251|0)[97]\\d{8}$");


    public Users createUser(UserRegistrationReq userReq, Role role) {
        return Users.builder()
                .username(userReq.getPhoneNumber())
                .password(passwordEncoder.encode(userReq.getPassword()))
                .fullName(userReq.getFullName())
                .phoneNumber(userReq.getPhoneNumber())
                .role(role)
                .userStatus(Status.ACTIVE)
                .isEnabled(true)
                .build();
    }

    public boolean isPhoneNumberTaken(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        boolean isValid = PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
        if (!isValid)
            throw new BadRequestException("Invalid phone number format.");

        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public void validateOldPassword(Users user, String oldPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!isPasswordMatch) {
            throw new BadRequestException("Incorrect old Password!");
        }
    }

    public Users getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponse updateUser(Users user) {

        user.setUpdatedAt(LocalDateTime.now().toString());
        user.setUpdatedBy(currentLoggedInUser.getUser().getFullName());

        Users savedUser = userRepository.save(user);
        return UserMapper.toUserResponse(savedUser);
    }
}
