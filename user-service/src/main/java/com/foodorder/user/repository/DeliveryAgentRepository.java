package com.foodorder.user.repository;

import com.foodorder.user.entity.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Long> {
}
