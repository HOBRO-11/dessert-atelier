package com.yangnjo.dessert_atelier.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;

public interface DisplayProductRepository extends JpaRepository<DisplayProduct, Long> {
}
