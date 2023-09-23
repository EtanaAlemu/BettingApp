package com.bettingApp.utils;

import com.bettingApp.exceptions.customExceptions.ResourceNotFoundException;
import com.bettingApp.exceptions.customExceptions.UnauthorizedException;
import com.bettingApp.userManager.user.UserRepository;
import com.bettingApp.userManager.user.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentLoggedInUser {

    private final UserRepository userRepository;

    public Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new UnauthorizedException("Access denied. Please provide a valid authentication token.");

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Please login with your phone number and try again."));
        // If a user changes his or her phone number, he or she must log in again.
    }
}