package com.academy.project.demo.repository;

import com.academy.project.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByStripeChargeId(String stripeChargeId);
    List<Order> findAllByUserId(Long id);
}
