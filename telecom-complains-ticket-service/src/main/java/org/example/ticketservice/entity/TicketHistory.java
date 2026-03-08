package org.example.ticketservice.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_history")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TicketHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Ticket ticket;
    @Enumerated(EnumType.STRING) @Column(name = "old_status")
    private TicketStatus oldStatus;
    @Enumerated(EnumType.STRING) @Column(name = "new_status", nullable = false)
    private TicketStatus newStatus;
    @CreationTimestamp @Column(name = "changed_at", updatable = false)
    private LocalDateTime changedAt;
    @Column(name = "changed_by_agent_id")
    private Long changedByAgentId;
    @Column
    private String note;
}
