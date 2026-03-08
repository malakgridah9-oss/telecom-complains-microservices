package org.example.ticketservice.dto;
import lombok.Data;
import org.example.ticketservice.entity.TicketStatus;
import java.time.LocalDateTime;
@Data
public class TicketHistoryResponse {
    private Long historyId;
    private Long ticketId;
    private TicketStatus oldStatus;
    private TicketStatus newStatus;
    private LocalDateTime changedAt;
    private Long changedByAgentId;
    private String note;
}
