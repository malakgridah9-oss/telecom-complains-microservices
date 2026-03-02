package org.example.telecomcomplainscomplaintservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "account", schema = "telecom_ticketing")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Integer id;

    @Column(name = "customer_id")
    private Integer customerId;

    @Size(max = 50)
    @Column(name = "account_code", length = 50)
    private String accountCode;

    @Size(max = 20)
    @Column(name = "account_type", length = 20)
    private String accountType;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;


}