package com.bettingApp.userManager.account;

import com.bettingApp.userManager.user.UserRepository;
import com.bettingApp.userManager.user.UserUtils;
import com.bettingApp.userManager.user.Users;
import com.bettingApp.userManager.user.dto.ChangePassword;
import com.bettingApp.utils.ApiResponse;
import com.bettingApp.utils.CurrentLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final CurrentLoggedInUser currentLoggedInUser;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> changePassword(ChangePassword temp) {
        Users user = currentLoggedInUser.getUser();

        userUtils.validateOldPassword(user, temp.getOldPassword());

        user.setPassword(passwordEncoder.encode(temp.getNewPassword()));
        user.setUpdatedBy(user.getFullName());

        userRepository.save(user);

        return ApiResponse.success("Password Changed Successfully!");
    }
}
