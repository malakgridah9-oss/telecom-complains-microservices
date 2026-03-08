package org.example.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.entity.TelecomService;
import org.example.customerservice.exception.*;
import org.example.customerservice.repository.TelecomServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TelecomServiceManager {

    private final TelecomServiceRepository repo;

    public TelecomServiceResponse create(TelecomServiceRequest req) {
        if (repo.existsByName(req.getName()))
            throw new DuplicateResourceException("Service already exists: " + req.getName());
        return map(repo.save(TelecomService.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .build()));
    }

    public TelecomServiceResponse getById(Long id) { return map(find(id)); }

    @Transactional(readOnly = true)
    public List<TelecomServiceResponse> getAll() {
        return repo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    public TelecomServiceResponse update(Long id, TelecomServiceRequest req) {
        TelecomService s = find(id);
        s.setName(req.getName());
        s.setDescription(req.getDescription());
        s.setPrice(req.getPrice());
        return map(repo.save(s));
    }

    public void delete(Long id) { repo.deleteById(id); }

    private TelecomService find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found: " + id));
    }

    private TelecomServiceResponse map(TelecomService s) {
        TelecomServiceResponse r = new TelecomServiceResponse();
        r.setId(s.getId()); r.setName(s.getName());
        r.setDescription(s.getDescription()); r.setPrice(s.getPrice());
        return r;
    }
}
