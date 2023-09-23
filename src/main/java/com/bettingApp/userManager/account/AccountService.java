package com.bettingApp.userManager.account;

import com.bettingApp.userManager.user.dto.ChangePassword;
import com.bettingApp.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<ApiResponse> changePassword(ChangePassword temp);
}
