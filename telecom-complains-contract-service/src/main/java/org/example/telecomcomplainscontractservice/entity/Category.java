package org.example.telecomcomplainscontractservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category", schema = "telecom_ticketing")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;


}