package org.example.telecomcomplainsauthentservice.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String role;
    private Integer customerId;
    private Integer agentId;
    private String phone;      // NOUVEAU — obligatoire
    private String address;
    private String cin;
}