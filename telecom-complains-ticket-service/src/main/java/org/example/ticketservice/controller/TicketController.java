package org.example.ticketservice.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ticketservice.dto.*;
import org.example.ticketservice.entity.*;
import org.example.ticketservice.service.TicketManager;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketManager ticketManager;

    @PostMapping
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody TicketRequest req) {
        return new ResponseEntity<>(ticketManager.create(req), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAll() { return ResponseEntity.ok(ticketManager.getAll()); }
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getById(@PathVariable Long id) { return ResponseEntity.ok(ticketManager.getById(id)); }
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> update(@PathVariable Long id, @Valid @RequestBody TicketRequest req) {
        return ResponseEntity.ok(ticketManager.update(id, req));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ticketManager.delete(id); return ResponseEntity.ok("Ticket deleted: " + id);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TicketResponse>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(ticketManager.getByCustomerId(customerId));
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketResponse>> getByStatus(@PathVariable TicketStatus status) {
        return ResponseEntity.ok(ticketManager.getByStatus(status));
    }
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TicketResponse>> getByPriority(@PathVariable TicketPriority priority) {
        return ResponseEntity.ok(ticketManager.getByPriority(priority));
    }
    @GetMapping("/search")
    public ResponseEntity<List<TicketResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(ticketManager.search(keyword));
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusRequest req) {
        return ResponseEntity.ok(ticketManager.updateStatus(id, req));
    }
    @PatchMapping("/{id}/assign/{agentId}")
    public ResponseEntity<TicketResponse> assignAgent(@PathVariable Long id, @PathVariable Long agentId) {
        return ResponseEntity.ok(ticketManager.assignAgent(id, agentId));
    }
    @GetMapping("/{id}/history")
    public ResponseEntity<List<TicketHistoryResponse>> getHistory(@PathVariable Long id) {
        return ResponseEntity.ok(ticketManager.getHistory(id));
    }
}
