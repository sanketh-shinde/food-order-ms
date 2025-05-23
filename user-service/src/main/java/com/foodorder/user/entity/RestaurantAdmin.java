package com.foodorder.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant_admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantAdmin {

    @Id
    @SequenceGenerator(name = "restaurant_admin_seq", sequenceName = "restaurant_admin_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_admin_seq")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_restaurant_admin_user")
    )
    private User user;

    // Add any custom fields if needed

}

