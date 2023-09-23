package com.bettingApp.userManager.user;

import com.bettingApp.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.bettingApp.userManager.role.Role;
import com.bettingApp.userManager.role.RoleService;
import com.bettingApp.userManager.user.dto.UserMapper;
import com.bettingApp.userManager.user.dto.UserRegistrationReq;
import com.bettingApp.userManager.user.dto.UserResponse;
import com.bettingApp.userManager.user.dto.UserUpdateReq;
import com.bettingApp.utils.ApiResponse;
import com.bettingApp.utils.CurrentLoggedInUser;
import com.bettingApp.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserUtils userUtils;
    private final CurrentLoggedInUser currentLoggedInUser;

    @Override
    public UserResponse me() {
        Users user = currentLoggedInUser.getUser();
        return UserMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getById(Long userId) {
        Users user = userUtils.getById(userId);
        return UserMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream().map(UserMapper::toUserResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse register(UserRegistrationReq userReq) {
        if (userUtils.isPhoneNumberTaken(userReq.getPhoneNumber()))
            throw new ResourceAlreadyExistsException("Phone number is already taken");

        Role role = roleService.getRoleByName("USER");
        Users user = userUtils.createUser(userReq, role);
        Users savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse editUser(UserUpdateReq updateReq) {
        Users user = currentLoggedInUser.getUser();

        if (updateReq.getFullName() != null)
            user.setFullName(updateReq.getFullName());

        // Update phone number if provided and different from the current phone number
        if (updateReq.getPhoneNumber() != null && !user.getPhoneNumber().equals(updateReq.getPhoneNumber())) {
            // Check if the new phone number is already taken
            if (userUtils.isPhoneNumberTaken(updateReq.getPhoneNumber()))
                throw new ResourceAlreadyExistsException("Phone number is already taken");

            user.setPhoneNumber(updateReq.getPhoneNumber());
        }

        return userUtils.updateUser(user);
    }

    @Override
    public UserResponse blockUser(Long id) {
        Users user = userUtils.getById(id);

        user.setUserStatus(Status.BLOCKED);
        return userUtils.updateUser(user);
    }

    @Override
    public UserResponse activateUser(Long id) {
        Users user = userUtils.getById(id);

        user.setUserStatus(Status.ACTIVE);
        return userUtils.updateUser(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> delete(Long id) {
        Users user = userUtils.getById(id);

        // Set user attributes for deletion
        user.setUserStatus(Status.DELETED);
        user.setUpdatedAt(LocalDateTime.now().toString());
        user.setUpdatedBy(currentLoggedInUser.getUser().getFullName());

        userRepository.save(user);
        return ApiResponse.success("User Deleted Successfully!");
    }

}
