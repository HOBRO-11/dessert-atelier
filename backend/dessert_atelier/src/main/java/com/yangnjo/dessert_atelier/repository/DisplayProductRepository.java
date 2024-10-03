package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain.DisplayProducts;
import com.yangnjo.dessert_atelier.repository.query_dsl.DpQueryDslRepo;

public interface DisplayProductRepository extends JpaRepository<DisplayProducts, Long>, DpQueryDslRepo {

}
