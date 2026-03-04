package org.example.telecomcomplainscontractservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "priority", schema = "telecom_ticketing")
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "sla_hours")
    private Integer slaHours;


}