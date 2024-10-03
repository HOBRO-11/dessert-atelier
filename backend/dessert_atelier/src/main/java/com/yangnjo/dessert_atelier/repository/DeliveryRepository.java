package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.Deliveries;
import com.yangnjo.dessert_atelier.repository.query_dsl.DeliveryQueryDslRepo;

@Repository
public interface DeliveryRepository extends JpaRepository<Deliveries, String>, DeliveryQueryDslRepo {

}
