package com.yangnjo.dessert_atelier.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.product.ProductOption;

@Repository
public interface OptionRepository extends JpaRepository<ProductOption, Long> {
}
