package org.example.customerservice.repository;

import org.example.customerservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Rechercher un compte par username
    Optional<Account> findByUsername(String username);

    // Rechercher un compte par customer_id
    Optional<Account> findByCustomerId(Long customerId);

    // Rechercher un compte par account_code
    Optional<Account> findByAccountCode(String accountCode);

    // Vérifier si username existe déjà
    boolean existsByUsername(String username);

    // Vérifier si account_code existe déjà
    boolean existsByAccountCode(String accountCode);

    // Tous les comptes actifs
    List<Account> findByIsActiveTrue();

    // Tous les comptes d'un client
    List<Account> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    // Compter les comptes par type
    @Query("SELECT a.accountType, COUNT(a) FROM Account a GROUP BY a.accountType")
    List<Object[]> countAccountsByType();

    // Solde total de tous les comptes
    @Query("SELECT SUM(a.balance) FROM Account a")
    BigDecimal getTotalBalance();
}