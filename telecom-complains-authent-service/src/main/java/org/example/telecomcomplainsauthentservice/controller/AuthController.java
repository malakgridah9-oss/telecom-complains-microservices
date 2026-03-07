package org.example.telecomcomplainsauthentservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainsauthentservice.dto.AuthResponse;
import org.example.telecomcomplainsauthentservice.dto.LoginRequest;
import org.example.telecomcomplainsauthentservice.dto.RegisterRequest;
import org.example.telecomcomplainsauthentservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
