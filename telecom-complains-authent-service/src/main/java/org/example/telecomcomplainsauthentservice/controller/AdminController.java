package org.example.telecomcomplainsauthentservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainsauthentservice.dto.AdminAgentRequest;
import org.example.telecomcomplainsauthentservice.dto.AgentResponse;
import org.example.telecomcomplainsauthentservice.entity.User;
import org.example.telecomcomplainsauthentservice.repository.UserRepository;
import org.example.telecomcomplainsauthentservice.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService   adminService;
    private final UserRepository userRepository;

    // ─── Créer un agent ───────────────────────────────────────
    @PostMapping("/agents")
    public ResponseEntity<?> createAgent(@RequestBody AdminAgentRequest request) {
        return ResponseEntity.ok(adminService.createAgent(request));
    }

    // ─── Liste tous les agents non supprimés ──────────────────
    @GetMapping("/agents")
    public ResponseEntity<List<AgentResponse>> getAllAgents() {
        return ResponseEntity.ok(adminService.getAllAgents());
    }

    // ─── Supprimer agent des deux tables + désassigner tickets ─
    @DeleteMapping("/agents/{id}")
    public ResponseEntity<?> deleteAgent(@PathVariable Integer id) {
        adminService.deleteAgent(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Agent supprimé"));
    }

    // ─── Réactiver un agent ───────────────────────────────────
    @PutMapping("/agents/{id}/activate")
    public ResponseEntity<?> activateAgent(@PathVariable Integer id) {
        adminService.activateAgent(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Agent activé"));
    }

    // ─── Liste tous les users ─────────────────────────────────
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ─── ✅ Liste uniquement les clients inscrits (role=CLIENT) ─
    @GetMapping("/users/clients")
    public ResponseEntity<List<User>> getAllClients() {
        List<User> clients = userRepository.findAll().stream()
                .filter(u -> User.Role.CLIENT.equals(u.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(clients);
    }

    // ─── Liste users PENDING ──────────────────────────────────
    @GetMapping("/users/pending")
    public ResponseEntity<List<User>> getPendingUsers() {
        List<User> pending = userRepository.findAll().stream()
                .filter(u -> Boolean.FALSE.equals(u.getIsApproved())
                        && Boolean.TRUE.equals(u.getIsActive()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(pending);
    }

    // ─── Approuver un user ────────────────────────────────────
    @PutMapping("/users/{id}/approve")
    public ResponseEntity<Map<String, Object>> approveUser(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        user.setIsApproved(true);
        user.setIsActive(true);
        user.setApprovedAt(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User approved",
                "userId",  id,
                "email",   user.getEmail()
        ));
    }

    // ─── Rejeter un user ──────────────────────────────────────
    @PutMapping("/users/{id}/reject")
    public ResponseEntity<Map<String, Object>> rejectUser(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        user.setIsApproved(false);
        user.setIsActive(false);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User rejected",
                "userId",  id,
                "email",   user.getEmail()
        ));
    }

    // ─── Changer le rôle ──────────────────────────────────────
    @PutMapping("/users/{id}/role")
    public ResponseEntity<Map<String, Object>> changeRole(
            @PathVariable Integer id,
            @RequestParam String role) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        user.setRole(User.Role.valueOf(role.toUpperCase()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Role updated",
                "userId",  id,
                "role",    role.toUpperCase()
        ));
    }

    // ─── Activer / Désactiver ─────────────────────────────────
    @PutMapping("/users/{id}/toggle-active")
    public ResponseEntity<Map<String, Object>> toggleActive(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        user.setIsActive(!user.getIsActive());
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "success",  true,
                "message",  "Active status updated",
                "userId",   id,
                "isActive", user.getIsActive()
        ));
    }
}