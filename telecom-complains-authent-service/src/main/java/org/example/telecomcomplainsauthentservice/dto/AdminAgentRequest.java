package org.example.telecomcomplainsauthentservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminAgentRequest {
    private String fullName;
    private String email;
    private String phone;  // ⭐ AJOUTER CE CHAMP
    private String category;
    private String password;
    private String role;
    private LocalDateTime createdAt;



}