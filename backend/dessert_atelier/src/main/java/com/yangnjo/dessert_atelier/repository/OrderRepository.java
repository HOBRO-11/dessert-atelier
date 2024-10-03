package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.Orders;
import com.yangnjo.dessert_atelier.repository.query_dsl.OrderQueryDslRepo;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String>, OrderQueryDslRepo {

}
