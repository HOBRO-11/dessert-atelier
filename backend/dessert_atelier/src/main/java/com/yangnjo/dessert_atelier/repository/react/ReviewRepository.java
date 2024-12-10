package com.yangnjo.dessert_atelier.repository.react;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.react.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}