package com.yangnjo.dessert_atelier.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.db.entity.Deliveries;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.DeliveryQueryDslRepo;

@Repository
public interface DeliveryRepository extends JpaRepository<Deliveries, String>, DeliveryQueryDslRepo {

}
