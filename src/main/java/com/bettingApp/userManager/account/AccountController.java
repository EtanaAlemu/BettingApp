package com.bettingApp.userManager.account;

import com.bettingApp.userManager.user.dto.ChangePassword;
import com.bettingApp.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/password/change")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody @Valid ChangePassword changePassword) {
        return accountService.changePassword(changePassword);
    }
}
