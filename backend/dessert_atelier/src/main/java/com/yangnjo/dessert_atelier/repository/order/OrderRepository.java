package com.yangnjo.dessert_atelier.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.order.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    boolean existsByOrderCode(Long orderCode);

}
