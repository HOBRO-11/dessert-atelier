package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.Products;
import com.yangnjo.dessert_atelier.repository.query_dsl.ProductQueryDslRepo;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long>, ProductQueryDslRepo {

}
