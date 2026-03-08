package org.example.customerservice.repository;
import org.example.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.fullName) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
           "LOWER(c.email)    LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
           "LOWER(c.phone)    LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Customer> searchByKeyword(String keyword);
}
