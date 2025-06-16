package com.foodorder.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees",
        uniqueConstraints = @UniqueConstraint(name = "uk_employee_code", columnNames = "employee_code")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_employee_user")
    )
    private User user;

    @Column(nullable = false)
    private String department;

    @Column(name = "employee_code", nullable = false, unique = true)
    private String employeeCode;

    @Column(nullable = false)
    private boolean onDuty;

}
