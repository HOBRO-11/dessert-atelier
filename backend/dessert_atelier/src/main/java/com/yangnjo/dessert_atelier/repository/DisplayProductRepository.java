package com.yangnjo.dessert_atelier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;

public interface DisplayProductRepository extends JpaRepository<DisplayProduct, Long> {

  Optional<DisplayProduct> findByNaming(String naming);

  boolean existsByNaming(String naming);

}
