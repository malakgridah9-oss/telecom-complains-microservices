package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}