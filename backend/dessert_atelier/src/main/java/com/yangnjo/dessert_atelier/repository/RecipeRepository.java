package com.yangnjo.dessert_atelier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.Components;
import com.yangnjo.dessert_atelier.domain.Products;
import com.yangnjo.dessert_atelier.domain.Recipes;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Long> {
    
    List<Recipes> findByProducts(Products products);

    List<Recipes> findByComponents(Components components);
}
