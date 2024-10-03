package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.QnAs;
import com.yangnjo.dessert_atelier.repository.query_dsl.QnAQueryDslRepo;

@Repository
public interface QnARepository extends JpaRepository<QnAs, Long>, QnAQueryDslRepo {

}
