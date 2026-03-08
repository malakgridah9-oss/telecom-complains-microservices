package org.example.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.example.ticketservice.client.CustomerClient;
import org.example.ticketservice.client.ContractClient;
import org.example.ticketservice.dto.*;
import org.example.ticketservice.entity.*;
import org.example.ticketservice.exception.ResourceNotFoundException;
import org.example.ticketservice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketManager {

    private final TicketRepository        ticketRepo;
    private final TicketHistoryRepository historyRepo;
    private final CustomerClient          customerClient;
    private final ContractClient          contractClient;

    public TicketResponse create(TicketRequest req) {
        CustomerResponse customer = customerClient.getCustomerById(req.getCustomerId());

        Ticket ticket = Ticket.builder()
                .title(req.getTitle()).description(req.getDescription())
                .priority(req.getPriority())
                .status(req.getAgentId() != null ? TicketStatus.IN_PROGRESS : TicketStatus.OPEN)
                .customerId(req.getCustomerId()).agentId(req.getAgentId())
                .category(req.getCategory()).resolutionNote(req.getResolutionNote())
                .build();

        Ticket saved = ticketRepo.save(ticket);

        historyRepo.save(TicketHistory.builder()
                .ticket(saved).oldStatus(null).newStatus(saved.getStatus())
                .changedByAgentId(req.getAgentId()).note("Ticket created").build());

        return map(saved, customer);
    }

    @Transactional(readOnly = true)
    public TicketResponse getById(Long id) {
        Ticket t = find(id);
        return map(t, customerClient.getCustomerById(t.getCustomerId()));
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getAll() {
        return ticketRepo.findAll().stream()
                .map(t -> map(t, customerClient.getCustomerById(t.getCustomerId())))
                .collect(Collectors.toList());
    }

    public TicketResponse update(Long id, TicketRequest req) {
        Ticket t = find(id);
        t.setTitle(req.getTitle()); t.setDescription(req.getDescription());
        t.setPriority(req.getPriority()); t.setCategory(req.getCategory());
        t.setResolutionNote(req.getResolutionNote());
        return map(ticketRepo.save(t), customerClient.getCustomerById(t.getCustomerId()));
    }

    public void delete(Long id) { ticketRepo.delete(find(id)); }

    @Transactional(readOnly = true)
    public List<TicketResponse> getByCustomerId(Long customerId) {
        CustomerResponse customer = customerClient.getCustomerById(customerId);
        return ticketRepo.findByCustomerId(customerId)
                .stream().map(t -> map(t, customer)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getByStatus(TicketStatus status) {
        return ticketRepo.findByStatus(status).stream()
                .map(t -> map(t, customerClient.getCustomerById(t.getCustomerId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getByPriority(TicketPriority priority) {
        return ticketRepo.findByPriority(priority).stream()
                .map(t -> map(t, customerClient.getCustomerById(t.getCustomerId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> search(String keyword) {
        return ticketRepo.searchByKeyword(keyword).stream()
                .map(t -> map(t, customerClient.getCustomerById(t.getCustomerId())))
                .collect(Collectors.toList());
    }

    public TicketResponse updateStatus(Long ticketId, UpdateStatusRequest req) {
        Ticket t = find(ticketId);
        TicketStatus oldStatus = t.getStatus();
        t.setStatus(req.getStatus());
        if (req.getNote() != null) t.setResolutionNote(req.getNote());
        if (req.getStatus() == TicketStatus.RESOLVED) t.setResolvedAt(LocalDateTime.now());

        historyRepo.save(TicketHistory.builder()
                .ticket(t).oldStatus(oldStatus).newStatus(req.getStatus())
                .changedByAgentId(req.getAgentId()).note(req.getNote()).build());

        return map(ticketRepo.save(t), customerClient.getCustomerById(t.getCustomerId()));
    }

    public TicketResponse assignAgent(Long ticketId, Long agentId) {
        Ticket t = find(ticketId);
        TicketStatus oldStatus = t.getStatus();
        t.setAgentId(agentId);
        if (t.getStatus() == TicketStatus.OPEN) t.setStatus(TicketStatus.IN_PROGRESS);

        historyRepo.save(TicketHistory.builder()
                .ticket(t).oldStatus(oldStatus).newStatus(t.getStatus())
                .changedByAgentId(agentId).note("Agent assigned: " + agentId).build());

        return map(ticketRepo.save(t), customerClient.getCustomerById(t.getCustomerId()));
    }

    @Transactional(readOnly = true)
    public List<TicketHistoryResponse> getHistory(Long ticketId) {
        return historyRepo.findByTicketIdOrderByChangedAtAsc(ticketId)
                .stream().map(this::mapHistory).collect(Collectors.toList());
    }

    private Ticket find(Long id) {
        return ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found: " + id));
    }

    private TicketResponse map(Ticket t, CustomerResponse c) {
        TicketResponse r = new TicketResponse();
        r.setId(t.getId()); r.setTitle(t.getTitle()); r.setDescription(t.getDescription());
        r.setStatus(t.getStatus()); r.setPriority(t.getPriority()); r.setCategory(t.getCategory());
        r.setResolutionNote(t.getResolutionNote()); r.setAgentId(t.getAgentId());
        r.setCreatedAt(t.getCreatedAt()); r.setUpdatedAt(t.getUpdatedAt()); r.setResolvedAt(t.getResolvedAt());
        r.setCustomerId(t.getCustomerId());
        if (c != null) { r.setCustomerFullName(c.getFullName()); r.setCustomerEmail(c.getEmail()); }
        return r;
    }

    private TicketHistoryResponse mapHistory(TicketHistory h) {
        TicketHistoryResponse r = new TicketHistoryResponse();
        r.setHistoryId(h.getHistoryId()); r.setTicketId(h.getTicket().getId());
        r.setOldStatus(h.getOldStatus()); r.setNewStatus(h.getNewStatus());
        r.setChangedAt(h.getChangedAt()); r.setChangedByAgentId(h.getChangedByAgentId()); r.setNote(h.getNote());
        return r;
    }
}
