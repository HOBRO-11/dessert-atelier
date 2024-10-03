package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.Carts;
import com.yangnjo.dessert_atelier.repository.query_dsl.CartQueryDslRepo;
@Repository
public interface CartRepository extends JpaRepository<Carts, Long>, CartQueryDslRepo {

}
