package org.example.ticketservice.controller;

import org.example.ticketservice.entity.Agent;
import org.example.ticketservice.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agents")
@CrossOrigin(origins = "http://localhost:4200")
public class AgentController {

    @Autowired
    private AgentRepository agentRepository;

    @GetMapping
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Agent getAgentById(@PathVariable Integer id) {
        return agentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Agent createAgent(@RequestBody Agent agent) {
        return agentRepository.save(agent);
    }

    @PutMapping("/{id}")
    public Agent updateAgent(@PathVariable Integer id,
                             @RequestBody Agent agent) {
        agent.setAgentId(id);
        return agentRepository.save(agent);
    }

    @DeleteMapping("/{id}")
    public void deleteAgent(@PathVariable Integer id) {
        agentRepository.deleteById(id);
    }
}