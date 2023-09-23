package com.bettingApp.userManager.user;

import com.bettingApp.userManager.user.dto.UserRegistrationReq;
import com.bettingApp.userManager.user.dto.UserResponse;
import com.bettingApp.userManager.user.dto.UserUpdateReq;
import com.bettingApp.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(userService.me());
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegistrationReq userReq) {
        UserResponse user = userService.register(userReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    public ResponseEntity<UserResponse> editUser(@RequestBody @Valid UserUpdateReq updateReq) {
        return ResponseEntity.ok(userService.editUser(updateReq));
    }

    @PutMapping("/block")
    public ResponseEntity<UserResponse> blockUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.blockUser(id));
    }

    @PutMapping("/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.activateUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }

}


