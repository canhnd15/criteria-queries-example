package com.davidnguyen.criteriaqueries.department.persistence;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
