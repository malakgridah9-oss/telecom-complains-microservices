package org.example.telecomcomplainscontractservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer", schema = "telecom_ticketing")
public class Customer {
    @Id
    @Column(name = "customer_id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 100)
    @Column(name = "full_name", length = 100)
    private String fullName;

    @jakarta.validation.constraints.Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @jakarta.validation.constraints.Size(max = 200)
    @Column(name = "address", length = 200)
    private String address;


}