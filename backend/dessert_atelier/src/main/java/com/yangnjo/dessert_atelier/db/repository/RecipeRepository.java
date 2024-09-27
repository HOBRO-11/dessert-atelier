package com.yangnjo.dessert_atelier.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.db.entity.Components;
import com.yangnjo.dessert_atelier.db.entity.Products;
import com.yangnjo.dessert_atelier.db.entity.Recipes;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Long> {
    
    List<Recipes> findByProducts(Products products);

    List<Recipes> findByComponents(Components components);
}
