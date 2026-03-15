package org.example.ticketservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ticket", schema = "telecom_ticketing")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private Integer id;


    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "contract_id")
    private Integer contractId;

    @Column(name = "contract_service_id")
    private Integer contractServiceId;

    @Size(max = 50)
    @Column(name = "category", length = 50)
    private String category;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "assigned_agent_id")
    private Integer assignedAgentId;


}