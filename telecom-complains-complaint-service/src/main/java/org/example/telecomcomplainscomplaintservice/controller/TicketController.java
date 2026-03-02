package org.example.telecomcomplainscomplaintservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscomplaintservice.entity.Ticket;
import org.example.telecomcomplainscomplaintservice.entity.TicketHistory;
import org.example.telecomcomplainscomplaintservice.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Ticket>> getTicketsByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(ticketService.getTicketsByCustomer(customerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Ticket>> getTicketsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(ticketService.getTicketsByStatus(status));
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<Ticket>> getTicketsByAgent(@PathVariable Integer agentId) {
        return ResponseEntity.ok(ticketService.getTicketsByAgent(agentId));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<TicketHistory>> getTicketHistory(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketService.getTicketHistory(id));
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Ticket> updateStatus(@PathVariable Integer id,
                                               @RequestParam String status,
                                               @RequestParam Integer agentId) {
        return ResponseEntity.ok(ticketService.updateStatus(id, status, agentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}