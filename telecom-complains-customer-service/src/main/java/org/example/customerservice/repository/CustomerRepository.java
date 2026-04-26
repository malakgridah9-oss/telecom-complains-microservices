package org.example.customerservice.repository;

import org.example.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // ─── RECHERCHES STANDARD ──────────────────────────────────────
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    boolean existsByEmail(String email);

    // ⭐ Clients AVEC contrat (au moins un contrat)
    @Query(value = "SELECT DISTINCT c.* FROM customer c INNER JOIN contract ct ON c.customer_id = ct.customer_id", nativeQuery = true)
    List<Customer> findCustomersWithContracts();

    // ⭐ Clients SANS contrat
    @Query(value = "SELECT c.* FROM customer c WHERE c.customer_id NOT IN (SELECT DISTINCT ct.customer_id FROM contract ct)", nativeQuery = true)
    List<Customer> findCustomersWithoutContracts();

    // ⭐ Vérifier si un client a un contrat
    @Query(value = "SELECT COUNT(*) > 0 FROM contract ct WHERE ct.customer_id = :customerId", nativeQuery = true)
    boolean hasContract(@Param("customerId") Long customerId);

    // ⭐ Clients AVEC contrat ACTIF
    @Query(value = "SELECT DISTINCT c.* FROM customer c " +
            "INNER JOIN contract ct ON c.customer_id = ct.customer_id " +
            "WHERE ct.status IN ('ACTIF', 'ACTIVE')", nativeQuery = true)
    List<Customer> findCustomersWithActiveContract();

    // ⭐ Clients SANS contrat actif
    @Query(value = "SELECT c.* FROM customer c WHERE c.customer_id NOT IN " +
            "(SELECT DISTINCT ct.customer_id FROM contract ct WHERE ct.status IN ('ACTIF', 'ACTIVE'))", nativeQuery = true)
    List<Customer> findCustomersWithoutActiveContract();

    // ⭐ Compter les clients avec contrat actif
    @Query(value = "SELECT COUNT(DISTINCT c.customer_id) FROM customer c " +
            "INNER JOIN contract ct ON c.customer_id = ct.customer_id " +
            "WHERE ct.status IN ('ACTIF', 'ACTIVE')", nativeQuery = true)
    long countCustomersWithActiveContract();
}