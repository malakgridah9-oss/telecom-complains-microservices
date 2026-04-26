package org.example.telecomcomplainsauthentservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainsauthentservice.dto.AdminAgentRequest;
import org.example.telecomcomplainsauthentservice.dto.AgentResponse;
import org.example.telecomcomplainsauthentservice.entity.Agent;
import org.example.telecomcomplainsauthentservice.entity.User;
import org.example.telecomcomplainsauthentservice.repository.AgentRepository;
import org.example.telecomcomplainsauthentservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AgentRepository agentRepository;
    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    // ─── Créer un agent ───────────────────────────────────────
    @Transactional
    public Map<String, Object> createAgent(AdminAgentRequest request) {

        if (agentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé par un agent");
        }

        Agent agent = Agent.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .category(request.getCategory())
                .role("AGENT")
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
        agentRepository.save(agent);

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.AGENT)
                .isApproved(true)
                .isActive(true)
                .agentId(agent.getAgentId())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success",  true);
        response.put("message",  "Agent créé avec succès");
        response.put("agentId",  agent.getAgentId());
        response.put("email",    agent.getEmail());
        response.put("phone",    agent.getPhone());
        response.put("category", agent.getCategory());
        return response;
    }

    // ─── Liste tous les agents non supprimés ──────────────────
    public List<AgentResponse> getAllAgents() {
        return agentRepository.findAll().stream()
                .filter(agent -> !agent.isDeleted())
                .map(agent -> new AgentResponse(
                        agent.getAgentId(),
                        agent.getFullName(),
                        agent.getEmail(),
                        agent.getPhone(),
                        agent.getCategory(),
                        agent.getRole(),
                        agent.getCreatedAt(),
                        agent.isDeleted()
                ))
                .collect(Collectors.toList());
    }

    // ─── Supprimer agent des DEUX tables + désassigner tickets ─
    @Transactional
    public void deleteAgent(Integer id) {

        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé: " + id));

        // ✅ 1. Désassigner les tickets EN PREMIER (cross-database)
        //       avant toute suppression pour éviter FK constraint
        userRepository.unassignTicketsByAgentId(id);

        // ✅ 2. Supprimer le user lié dans table users
        userRepository.findByEmail(agent.getEmail())
                .ifPresent(user -> userRepository.delete(user));

        // ✅ 3. Supprimer l'agent EN DERNIER
        agentRepository.delete(agent);
    }

    // ─── Réactiver un agent ───────────────────────────────────
    @Transactional
    public void activateAgent(Integer id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé: " + id));

        agent.setIsDeleted(false);
        agentRepository.save(agent);

        userRepository.findByEmail(agent.getEmail())
                .ifPresent(user -> {
                    user.setIsActive(true);
                    userRepository.save(user);
                });
    }
}