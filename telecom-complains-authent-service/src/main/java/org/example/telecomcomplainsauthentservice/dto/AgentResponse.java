package org.example.telecomcomplainsauthentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentResponse {
    private Integer agentId;
    private String fullName;
    private String email;
    private String phone;
    private String category;
    private String role;
    private LocalDateTime createdAt;
    private boolean isDeleted;  // ⭐ AJOUTER
}