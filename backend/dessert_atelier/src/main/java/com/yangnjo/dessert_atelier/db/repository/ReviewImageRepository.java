package com.yangnjo.dessert_atelier.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.db.entity.ReviewImages;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImages, Long> {

}
