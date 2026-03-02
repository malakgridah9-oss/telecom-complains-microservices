
package org.example.telecomcomplainscomplaintservice.repository;

import org.example.telecomcomplainscomplaintservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}