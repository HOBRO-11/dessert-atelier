package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain.order.OrderCart;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
    
}
