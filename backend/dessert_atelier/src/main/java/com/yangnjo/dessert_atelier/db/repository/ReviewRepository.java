package com.yangnjo.dessert_atelier.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.db.entity.Reviews;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.ReviewQueryDslRepo;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long>, ReviewQueryDslRepo {

}