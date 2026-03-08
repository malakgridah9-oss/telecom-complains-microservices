package org.example.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.service.TelecomServiceManager;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final TelecomServiceManager serviceManager;

    @PostMapping
    public ResponseEntity<TelecomServiceResponse> create(@Valid @RequestBody TelecomServiceRequest req) {
        return new ResponseEntity<>(serviceManager.create(req), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TelecomServiceResponse>> getAll() {
        return ResponseEntity.ok(serviceManager.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelecomServiceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceManager.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelecomServiceResponse> update(@PathVariable Long id,
                                                          @Valid @RequestBody TelecomServiceRequest req) {
        return ResponseEntity.ok(serviceManager.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        serviceManager.delete(id);
        return ResponseEntity.ok("Service deleted: " + id);
    }
}
