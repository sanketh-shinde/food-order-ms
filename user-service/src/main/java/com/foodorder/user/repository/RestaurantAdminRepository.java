package com.foodorder.user.repository;

import com.foodorder.user.entity.RestaurantAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantAdminRepository extends JpaRepository<RestaurantAdmin, Long> {
}
