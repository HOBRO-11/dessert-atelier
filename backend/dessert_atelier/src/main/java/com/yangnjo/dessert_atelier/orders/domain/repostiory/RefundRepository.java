package com.yangnjo.dessert_atelier.orders.domain.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.orders.domain.entity.Refund;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long>{
    
}
