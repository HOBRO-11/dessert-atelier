package com.yangnjo.dessert_atelier.react.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.react.domain.entity.ProductQna;

@Repository
public interface ProductQnaRepository extends JpaRepository<ProductQna, Long> {

}
