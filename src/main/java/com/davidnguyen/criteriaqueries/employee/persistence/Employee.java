package com.davidnguyen.criteriaqueries.employee.persistence;

import com.davidnguyen.criteriaqueries.department.persistence.Department;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
