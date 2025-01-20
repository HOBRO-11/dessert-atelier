package com.yangnjo.dessert_atelier.react.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.react.domain.entity.ProductReact;

@Repository
public interface ProductReactRepository extends JpaRepository<ProductReact, Long> {
    
}
