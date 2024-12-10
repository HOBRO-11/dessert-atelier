package com.yangnjo.dessert_atelier.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain_model.product.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
