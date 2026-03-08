package org.example.ticketservice.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TicketStatus status;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TicketPriority priority;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "agent_id")
    private Long agentId;
    @Column
    private String category;
    @Column(name = "resolution_note", columnDefinition = "TEXT")
    private String resolutionNote;
    @CreationTimestamp @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<TicketHistory> history;
}
