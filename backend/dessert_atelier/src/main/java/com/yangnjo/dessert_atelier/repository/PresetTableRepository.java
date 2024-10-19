package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.PresetTable;

@Repository
public interface PresetTableRepository extends JpaRepository<PresetTable, Long> {

  boolean existsByDisplayProduct(DisplayProduct displayProduct);
  
}
