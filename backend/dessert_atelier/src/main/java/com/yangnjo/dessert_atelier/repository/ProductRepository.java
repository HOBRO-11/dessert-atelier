package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  boolean existsByName(String name);

}
