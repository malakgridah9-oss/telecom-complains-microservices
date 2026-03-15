package org.example.telecomcomplainscontractservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscontractservice.entity.ContractService;
import org.example.telecomcomplainscontractservice.entity.Service;
import org.example.telecomcomplainscontractservice.service.ServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceController {

    private final ServiceImpl serviceImpl;

    // GET tous les services
    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(
                serviceImpl.getAllServices());
    }

    // GET service par id
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
                serviceImpl.getServiceById(id));
    }

    // GET services par type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Service>> getServicesByType(
            @PathVariable String type) {
        return ResponseEntity.ok(
                serviceImpl.getServicesByType(type));
    }

    // GET services par contrat  ← retourne List<Service>
    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<Service>> getServicesByContract(
            @PathVariable Integer contractId) {
        return ResponseEntity.ok(
                serviceImpl.getServicesByContract(contractId));
    }

    // POST créer service
    @PostMapping
    public ResponseEntity<Service> createService(
            @RequestBody Service service) {
        return ResponseEntity.ok(
                serviceImpl.createService(service));
    }

    // POST activer service  ← retourne ContractService
    @PostMapping("/activate")
    public ResponseEntity<ContractService> activateService(
            @RequestParam Integer contractId,
            @RequestParam Integer serviceId) {
        return ResponseEntity.ok(
                serviceImpl.activateService(
                        contractId, serviceId));
    }

    // DELETE supprimer service
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Integer id) {
        serviceImpl.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}