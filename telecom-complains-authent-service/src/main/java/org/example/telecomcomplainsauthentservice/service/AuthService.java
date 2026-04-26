package org.example.telecomcomplainsauthentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainsauthentservice.dto.AuthResponse;
import org.example.telecomcomplainsauthentservice.dto.LoginRequest;
import org.example.telecomcomplainsauthentservice.dto.RegisterRequest;
import org.example.telecomcomplainsauthentservice.entity.Customer;
import org.example.telecomcomplainsauthentservice.entity.User;
import org.example.telecomcomplainsauthentservice.repository.CustomerRepository;
import org.example.telecomcomplainsauthentservice.repository.UserRepository;
import org.example.telecomcomplainsauthentservice.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // ─── REGISTER ────────────────────────────────────────────────
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // 1. Vérifier email unique
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists");
        }

        // 2. Vérifier que le CIN existe dans la table customer
        Customer existingCustomer = customerRepository.findByCin(request.getCin())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "CIN not found. You must be a registered customer."));

        // 3. Vérifier que ce customer n'a pas déjà un compte
        if (userRepository.existsByCustomerId(existingCustomer.getCustomerId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Account already exists for this CIN.");
        }

        // 4. Créer User lié au Customer existant
        User user = new User();
        user.setFullName(existingCustomer.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(existingCustomer.getPhone());
        user.setCin(request.getCin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.CLIENT);
        user.setIsApproved(true);   // ✅ CORRIGÉ : était false
        user.setIsActive(true);
        user.setCustomerId(existingCustomer.getCustomerId());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        return new AuthResponse(
                null,
                user.getEmail(),
                user.getRole().name(),
                user.getFullName(),
                existingCustomer.getCustomerId(),
                null,
                true   // ✅ CORRIGÉ : était false
        );
    }

    // ─── LOGIN ───────────────────────────────────────────────────
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Account is disabled");
        }

        if (Boolean.FALSE.equals(user.getIsApproved())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Account is disabled");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtils.generateToken(
                user.getEmail(), user.getRole().name());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name(),
                user.getFullName(),
                user.getCustomerId(),
                user.getAgentId(),
                true
        );
    }
}