package com.foodorder.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_agents",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_vehicle_number", columnNames = "vehicle_number"),
                @UniqueConstraint(name = "uk_license_number", columnNames = "license_number")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAgent {

    @Id
    @SequenceGenerator(name = "delivery_agent_seq", sequenceName = "delivery_agent_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_agent_seq")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_delivery_agent_user")
    )
    private User user;

    @Column(name = "vehicle_number", nullable = false, unique = true)
    private String vehicleNumber;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private boolean available = false;

}
