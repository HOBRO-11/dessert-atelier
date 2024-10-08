package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
