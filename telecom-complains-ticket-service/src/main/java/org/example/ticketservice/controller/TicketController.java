package org.example.ticketservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.ticketservice.dto.*;
import org.example.ticketservice.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @RequestBody TicketRequest request) {
        return ResponseEntity.ok(
                ticketService.createTicket(request));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TicketResponse>> getByCustomer(
            @PathVariable Integer customerId) {
        return ResponseEntity.ok(
                ticketService.getTicketsByCustomer(customerId));
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<TicketResponse>> getByAgent(
            @PathVariable Integer agentId) {
        return ResponseEntity.ok(
                ticketService.getTicketsByAgent(agentId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketResponse>> getByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(
                ticketService.getTicketsByStatus(status));
    }

    @PutMapping("/{ticketId}/assign/{agentId}")
    public ResponseEntity<TicketResponse> assignAgent(
            @PathVariable Integer ticketId,
            @PathVariable Integer agentId) {
        return ResponseEntity.ok(
                ticketService.assignAgent(ticketId, agentId));
    }

    @PutMapping("/{ticketId}/status")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable Integer ticketId,
            @RequestParam String newStatus,
            @RequestParam(required = false) Integer agentId,
            @RequestParam(required = false) String comment) {
        return ResponseEntity.ok(ticketService.updateStatus(
                ticketId, newStatus, agentId, comment));
    }

    @GetMapping("/{ticketId}/history")
    public ResponseEntity<List<TicketHistoryResponse>> getHistory(
            @PathVariable Integer ticketId) {
        return ResponseEntity.ok(
                ticketService.getTicketHistory(ticketId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}