package com.yangnjo.dessert_atelier.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.db.entity.Products;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.ProductQueryDslRepo;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> , ProductQueryDslRepo{

}
