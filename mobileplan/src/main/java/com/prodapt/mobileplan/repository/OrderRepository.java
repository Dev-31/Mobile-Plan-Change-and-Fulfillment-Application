package com.prodapt.mobileplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.mobileplan.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
