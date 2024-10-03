package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yangnjo.dessert_atelier.domain.Options;
import com.yangnjo.dessert_atelier.repository.query_dsl.OptionQueryDslRepo;

public interface OptionRepository extends JpaRepository<Options, Long>, OptionQueryDslRepo{
    
}
