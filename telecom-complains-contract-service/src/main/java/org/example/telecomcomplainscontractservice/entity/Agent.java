package org.example.telecomcomplainscontractservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agent", schema = "telecom_ticketing")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 100)
    @Column(name = "full_name", length = 100)
    private String fullName;

    @jakarta.validation.constraints.Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @jakarta.validation.constraints.Size(max = 50)
    @Column(name = "role", length = 50)
    private String role;


}