package org.example.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.example.ticketservice.dto.TicketHistoryResponse;
import org.example.ticketservice.dto.TicketRequest;
import org.example.ticketservice.dto.TicketResponse;
import org.example.ticketservice.entity.Ticket;
import org.example.ticketservice.entity.TicketHistory;
import org.example.ticketservice.repository.TicketHistoryRepository;
import org.example.ticketservice.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketHistoryRepository historyRepository;

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setCustomerId(request.getCustomerId());
        ticket.setContractId(request.getContractId());
        ticket.setContractServiceId(request.getContractServiceId());
        ticket.setTitle(request.getTitle());
        ticket.setCategory(request.getCategory());
        ticket.setDescription(request.getDescription());
        ticket.setStatus("OPEN");
        ticket.setCreatedAt(Instant.now());
        return toResponse(ticketRepository.save(ticket));
    }

    @Override
    public TicketResponse getTicketById(Integer id) {
        return toResponse(ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found: " + id)));
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> getTicketsByCustomer(
            Integer customerId) {
        return ticketRepository.findByCustomerId(customerId)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> getTicketsByAgent(
            Integer agentId) {
        return ticketRepository
                .findByAssignedAgentId(agentId)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> getTicketsByStatus(
            String status) {
        return ticketRepository.findByStatus(status)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponse assignAgent(Integer ticketId,
                                      Integer agentId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));
        String oldStatus = ticket.getStatus();
        ticket.setAssignedAgentId(agentId);
        ticket.setStatus("IN_PROGRESS");
        ticketRepository.save(ticket);
        saveHistory(ticketId, agentId,
                oldStatus, "IN_PROGRESS");
        return toResponse(ticket);
    }

    @Override
    public TicketResponse updateStatus(Integer ticketId,
                                       String newStatus, Integer agentId, String comment) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));
        String oldStatus = ticket.getStatus();
        ticket.setStatus(newStatus);
        ticketRepository.save(ticket);
        saveHistory(ticketId, agentId, oldStatus, newStatus);
        return toResponse(ticket);
    }

    @Override
    public List<TicketHistoryResponse> getTicketHistory(
            Integer ticketId) {
        return historyRepository
                .findByTicketIdOrderByChangedAtDesc(ticketId)
                .stream().map(this::toHistoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }

    private void saveHistory(Integer ticketId,
                             Integer agentId, String oldStatus,
                             String newStatus) {
        TicketHistory history = new TicketHistory();
        history.setTicketId(ticketId);
        history.setChangedByAgentId(agentId);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setChangedAt(Instant.now());
        historyRepository.save(history);
    }

    private TicketResponse toResponse(Ticket ticket) {
        TicketResponse r = new TicketResponse();
        r.setTicketId(ticket.getId());
        r.setCustomerId(ticket.getCustomerId());
        r.setContractId(ticket.getContractId());
        r.setContractServiceId(ticket.getContractServiceId());
        r.setTitle(ticket.getTitle());
        r.setCategory(ticket.getCategory());
        r.setDescription(ticket.getDescription());
        r.setStatus(ticket.getStatus());
        r.setAssignedAgentId(ticket.getAssignedAgentId());
        r.setCreatedAt(ticket.getCreatedAt());
        return r;
    }

    private TicketHistoryResponse toHistoryResponse(
            TicketHistory h) {
        TicketHistoryResponse r = new TicketHistoryResponse();
        r.setHistoryId(h.getId());
        r.setTicketId(h.getTicketId());
        r.setOldStatus(h.getOldStatus());
        r.setNewStatus(h.getNewStatus());
        r.setChangedByAgentId(h.getChangedByAgentId());
        r.setChangedAt(h.getChangedAt());
        return r;
    }
}