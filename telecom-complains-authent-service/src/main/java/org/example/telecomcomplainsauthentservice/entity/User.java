package org.example.telecomcomplainsauthentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;

    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "agent_id")
    private Integer agentId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "approved_by")
    private Integer approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "cin")
    private String cin;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Role {
        ADMIN, AGENT, CLIENT
    }
}