package org.example.ticketservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ticketservice.dto.TicketHistoryResponse;
import org.example.ticketservice.dto.TicketRequest;
import org.example.ticketservice.dto.TicketResponse;
import org.example.ticketservice.entity.Agent;
import org.example.ticketservice.entity.Ticket;
import org.example.ticketservice.entity.TicketHistory;
import org.example.ticketservice.repository.AgentRepository;
import org.example.ticketservice.repository.TicketHistoryRepository;
import org.example.ticketservice.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository        ticketRepository;
    private final TicketHistoryRepository historyRepository;
    private final AgentRepository         agentRepository;

    // Ajoutez ces repositories si vous les avez
    // private final MessageRepository messageRepository;
    // private final NoteRepository noteRepository;

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setCustomerId(request.getCustomerId());
        ticket.setContractId(request.getContractId());
        ticket.setContractServiceId(request.getContractServiceId());
        ticket.setTitle(request.getTitle());
        ticket.setCategory(request.getCategory());
        ticket.setDescription(request.getDescription());
        ticket.setCreatedAt(Instant.now());

        if (request.getCategory() != null
                && !request.getCategory().equals("AUTRE")) {

            Optional<Agent> agent = agentRepository
                    .findFirstByCategoryAndIsDeletedFalse(
                            request.getCategory());

            if (agent.isPresent()) {
                ticket.setAssignedAgentId(agent.get().getAgentId());
                ticket.setStatus("IN_PROGRESS");
            } else {
                ticket.setStatus("OPEN");
            }
        } else {
            ticket.setStatus("OPEN");
            ticket.setAssignedAgentId(null);
        }

        Ticket saved = ticketRepository.save(ticket);

        if (saved.getAssignedAgentId() != null) {
            saveHistory(
                    saved.getId(),
                    saved.getAssignedAgentId(),
                    "OPEN", "IN_PROGRESS",
                    "Assigné automatiquement — catégorie: " + saved.getCategory()
            );
        }

        return toResponse(saved);
    }

    @Override
    public TicketResponse getTicketById(Integer id) {
        return toResponse(ticketRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found: " + id)));
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> getTicketsByCustomer(Integer customerId) {
        return ticketRepository.findByCustomerId(customerId)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> getTicketsByAgent(Integer agentId) {
        return ticketRepository.findByAssignedAgentId(agentId)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> getTicketsByStatus(String status) {
        return ticketRepository.findByStatus(status)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponse assignAgent(Integer ticketId, Integer agentId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        String oldStatus = ticket.getStatus();
        ticket.setAssignedAgentId(agentId);
        ticket.setStatus("IN_PROGRESS");
        ticketRepository.save(ticket);
        saveHistory(ticketId, agentId, oldStatus, "IN_PROGRESS",
                "Ticket assigné à l'agent #" + agentId);
        return toResponse(ticket);
    }

    @Override
    public TicketResponse updateStatus(Integer ticketId, String newStatus,
                                       Integer agentId, String comment) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        String oldStatus = ticket.getStatus();
        ticket.setStatus(newStatus);
        ticketRepository.save(ticket);
        saveHistory(ticketId, agentId, oldStatus, newStatus, comment);
        return toResponse(ticket);
    }

    @Override
    public List<TicketHistoryResponse> getTicketHistory(Integer ticketId) {
        return historyRepository
                .findByTicketIdOrderByChangedAtDesc(ticketId)
                .stream().map(this::toHistoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTicket(Integer id) {
        try {
            log.info("Tentative de suppression du ticket ID: {}", id);

            // Vérifier si le ticket existe
            Ticket ticket = ticketRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ticket not found: " + id));

            log.info("Ticket trouvé - ID: {}, Statut: {}, Catégorie: {}",
                    ticket.getId(), ticket.getStatus(), ticket.getCategory());

            // ⭐ 1. Supprimer l'historique lié à ce ticket
            List<TicketHistory> histories = historyRepository.findByTicketIdOrderByChangedAtDesc(id);
            if (histories != null && !histories.isEmpty()) {
                log.info("Suppression de {} entrées d'historique", histories.size());
                historyRepository.deleteAll(histories);
            }

            // ⭐ 2. Si vous avez une table de messages, supprimez-les
            // if (messageRepository != null) {
            //     List<Message> messages = messageRepository.findByTicketId(id);
            //     if (messages != null && !messages.isEmpty()) {
            //         log.info("Suppression de {} messages", messages.size());
            //         messageRepository.deleteAll(messages);
            //     }
            // }

            // ⭐ 3. Supprimer le ticket
            ticketRepository.deleteById(id);
            log.info("Ticket ID {} supprimé avec succès", id);

        } catch (Exception e) {
            log.error("Erreur lors de la suppression du ticket {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Impossible de supprimer le ticket: " + e.getMessage());
        }
    }

    // ── Helpers ───────────────────────────────────────────────
    private void saveHistory(Integer ticketId, Integer agentId,
                             String oldStatus, String newStatus,
                             String comment) {
        TicketHistory history = new TicketHistory();
        history.setTicketId(ticketId);
        history.setChangedByAgentId(agentId);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setComment(comment);
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

    private TicketHistoryResponse toHistoryResponse(TicketHistory h) {
        TicketHistoryResponse r = new TicketHistoryResponse();
        r.setHistoryId(h.getId());
        r.setTicketId(h.getTicketId());
        r.setOldStatus(h.getOldStatus());
        r.setNewStatus(h.getNewStatus());
        r.setChangedByAgentId(h.getChangedByAgentId());
        r.setChangedAt(h.getChangedAt());
        r.setComment(h.getComment());
        return r;
    }
}