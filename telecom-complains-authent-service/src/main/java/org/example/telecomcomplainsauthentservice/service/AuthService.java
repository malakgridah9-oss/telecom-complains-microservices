package org.example.telecomcomplainsauthentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainsauthentservice.dto.AuthResponse;
import org.example.telecomcomplainsauthentservice.dto.LoginRequest;
import org.example.telecomcomplainsauthentservice.dto.RegisterRequest;
import org.example.telecomcomplainsauthentservice.entity.User;
import org.example.telecomcomplainsauthentservice.repository.UserRepository;
import org.example.telecomcomplainsauthentservice.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRole() != null && !request.getRole().isEmpty()) {
            user.setRole(User.Role.valueOf(
                    request.getRole().toUpperCase()));
        }

        userRepository.save(user);

        String token = jwtUtils.generateToken(
                user.getEmail(), user.getRole().name());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name(),
                user.getFullName());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur non trouvé"));

        String token = jwtUtils.generateToken(
                user.getEmail(), user.getRole().name());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name(),
                user.getFullName());
    }
}