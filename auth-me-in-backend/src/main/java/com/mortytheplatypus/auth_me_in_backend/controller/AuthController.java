package com.mortytheplatypus.auth_me_in_backend.controller;

import com.mortytheplatypus.auth_me_in_backend.model.AuthResponse;
import com.mortytheplatypus.auth_me_in_backend.model.dto.LoginDto;
import com.mortytheplatypus.auth_me_in_backend.model.dto.RegisterDto;
import com.mortytheplatypus.auth_me_in_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDto registerDto) {
        AuthResponse response = authService.register(registerDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        AuthResponse response = authService.login(loginDto);

        return ResponseEntity.ok(response);
    }
}
