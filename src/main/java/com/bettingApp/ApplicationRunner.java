package com.bettingApp;

import com.bettingApp.userManager.role.Role;
import com.bettingApp.userManager.role.RoleRepository;
import com.bettingApp.userManager.user.UserRepository;
import com.bettingApp.userManager.user.Users;
import com.bettingApp.utils.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "database", name = "seed", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Initializes the database with preloaded data upon application startup.
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            try {
                // Create and save roles
                List<Role> roles = createRoles();
                List<Role> savedRoles = roleRepository.saveAll(roles);

                // Create and save user
                Users johnDoe = createUser(savedRoles.get(0));
                userRepository.save(johnDoe);

                log.info("ApplicationRunner => Preloaded authority, roles, and user");
            } catch (Exception ex) {
                log.error("ApplicationRunner Preloading Error: {}", ex.getMessage());
                throw new RuntimeException("ApplicationRunner Preloading Error ", ex);
            }
        };
    }

    private List<Role> createRoles() {
        Role admin = new Role("ADMIN", "System administrator with full control over app functionality, user access, and settings.");
        Role owner = new Role("USER", "Betting player.");
        return List.of(admin, owner);
    }


    private Users createUser(Role role) {
        return Users.builder()
                .username("user@bet.com")
                .password(passwordEncoder.encode("123456"))
                .fullName("John Doe")
                .phoneNumber("+251912345678")
                .role(role)
                .isEnabled(true)
                .userStatus(Status.ACTIVE)
                .build();
    }
}