package org.example.telecomcomplainscomplaintservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ticket_history", schema = "telecom_ticketing")
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false)
    private Integer id;

    @Column(name = "ticket_id")
    private Integer ticketId;

    @Size(max = 20)
    @Column(name = "old_status", length = 20)
    private String oldStatus;

    @Size(max = 20)
    @Column(name = "new_status", length = 20)
    private String newStatus;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "changed_at")
    private Instant changedAt;

    @Column(name = "changed_by_agent_id")
    private Integer changedByAgentId;


}