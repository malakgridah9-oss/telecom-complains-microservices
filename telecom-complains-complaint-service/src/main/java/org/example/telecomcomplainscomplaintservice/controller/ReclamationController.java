package org.example.telecomcomplainscomplaintservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscomplaintservice.entity.Reclamation;
import org.example.telecomcomplainscomplaintservice.service.ReclamationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationController {

    private final ReclamationService reclamationService;

    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        return ResponseEntity.ok(reclamationService.getAllReclamations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        return ResponseEntity.ok(reclamationService.getReclamationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reclamation>> getReclamationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reclamationService.getReclamationsByUser(userId));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Reclamation>> getReclamationsByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(reclamationService.getReclamationsByStatut(statut));
    }

    @PostMapping
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) {
        return ResponseEntity.ok(reclamationService.createReclamation(reclamation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable Long id,
                                                         @RequestBody Reclamation reclamation) {
        return ResponseEntity.ok(reclamationService.updateReclamation(id, reclamation));
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<Reclamation> updateStatut(@PathVariable Long id,
                                                    @RequestParam String statut) {
        return ResponseEntity.ok(reclamationService.updateStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.noContent().build();
    }
}