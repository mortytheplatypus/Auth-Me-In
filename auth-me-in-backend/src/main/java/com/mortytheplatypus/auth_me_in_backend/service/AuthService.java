package com.mortytheplatypus.auth_me_in_backend.service;

import com.mortytheplatypus.auth_me_in_backend.model.AuthResponse;
import com.mortytheplatypus.auth_me_in_backend.model.Role;
import com.mortytheplatypus.auth_me_in_backend.model.User;
import com.mortytheplatypus.auth_me_in_backend.model.dto.LoginDto;
import com.mortytheplatypus.auth_me_in_backend.model.dto.RegisterDto;
import com.mortytheplatypus.auth_me_in_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginDto loginDto) {
        try {
            String username = loginDto.username();
            String password = loginDto.password();

            User user = userRepository.findByUsername(loginDto.username())
                    .orElseThrow(() -> new Exception("User (" + username + ") not found"));

            if (Boolean.FALSE.equals(passwordEncoder.matches(password, user.getPassword()))) {
                return new AuthResponse(false, "Invalid password");
            } else {
                String token = jwtService.generateToken(user);
                return new AuthResponse(true, token);
            }
        } catch (Exception e) {
            return new AuthResponse(false, "Error: " + e.getMessage());
        }
    }

    public AuthResponse register(RegisterDto registerDto) {
        try {
            User user = createUser(registerDto);

            user = userRepository.save(user);

            String token = jwtService.generateToken(user);

            return new AuthResponse(true, token);
        } catch (Exception e) {
            return new AuthResponse(false, "Error: " + e.getMessage());
        }
    }

    private @NotNull User createUser(RegisterDto registerDto) {
        User user = new User();

        user.setName(registerDto.name());
        user.setEmail(registerDto.email());
        user.setUsername(registerDto.username());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setRole(Role.USER);

        if (registerDto.role() != null) {
            user.setRole(registerDto.role());
        }

        return user;
    }
}
