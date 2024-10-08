package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain.display_product.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

}
