package org.example.customerservice.repository;
import org.example.customerservice.entity.TelecomService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TelecomServiceRepository extends JpaRepository<TelecomService, Long> {
    Optional<TelecomService> findByName(String name);
    boolean existsByName(String name);
}
