package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain.DisplayProductImages;

public interface DisplayProductImageRepository extends JpaRepository<DisplayProductImages, Long> {
    
}
