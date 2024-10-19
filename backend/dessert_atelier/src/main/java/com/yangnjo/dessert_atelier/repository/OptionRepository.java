package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;

public interface OptionRepository extends JpaRepository<Option, Long> {

  boolean existsByDisplayProductPresetIdAndDescriptionAndOptionStatus(Long dppId, String description,
      OptionStatus status);

}
