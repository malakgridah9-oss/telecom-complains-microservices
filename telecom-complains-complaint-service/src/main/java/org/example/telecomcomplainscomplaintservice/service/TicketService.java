package org.example.telecomcomplainscomplaintservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscomplaintservice.entity.Ticket;
import org.example.telecomcomplainscomplaintservice.entity.TicketHistory;
import org.example.telecomcomplainscomplaintservice.repository.TicketRepository;
import org.example.telecomcomplainscomplaintservice.repository.TicketHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketHistoryRepository ticketHistoryRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Integer id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé : " + id));
    }

    public Ticket createTicket(Ticket ticket) {
        ticket.setStatus("OPEN");
        ticket.setCreatedAt(Instant.now());
        return ticketRepository.save(ticket);
    }

    public Ticket updateStatus(Integer id, String newStatus, Integer agentId) {
        Ticket ticket = getTicketById(id);

        TicketHistory history = new TicketHistory();
        history.setTicketId(id);
        history.setOldStatus(ticket.getStatus());
        history.setNewStatus(newStatus);
        history.setChangedAt(Instant.now());
        history.setChangedByAgentId(agentId);
        ticketHistoryRepository.save(history);

        ticket.setStatus(newStatus);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByCustomer(Integer customerId) {
        return ticketRepository.findByCustomerId(customerId);
    }

    public List<Ticket> getTicketsByStatus(String status) {
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> getTicketsByAgent(Integer agentId) {
        return ticketRepository.findByAssignedAgentId(agentId);
    }

    public List<TicketHistory> getTicketHistory(Integer ticketId) {
        return ticketHistoryRepository.findByTicketId(ticketId);
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }
}