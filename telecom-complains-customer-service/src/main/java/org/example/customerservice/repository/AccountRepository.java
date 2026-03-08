package org.example.customerservice.repository;
import org.example.customerservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByCustomerCustomerId(Long customerId);
    boolean existsByUsername(String username);
}
