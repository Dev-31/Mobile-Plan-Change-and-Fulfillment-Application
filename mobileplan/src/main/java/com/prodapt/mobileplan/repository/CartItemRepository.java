package com.prodapt.mobileplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.mobileplan.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
