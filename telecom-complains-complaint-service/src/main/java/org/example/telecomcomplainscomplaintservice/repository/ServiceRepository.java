package org.example.telecomcomplainscomplaintservice.repository;

import org.example.telecomcomplainscomplaintservice.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}