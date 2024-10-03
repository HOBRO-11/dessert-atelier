package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.Todos;
import com.yangnjo.dessert_atelier.repository.query_dsl.TodoQueryDslRepo;

@Repository
public interface TodoRepository extends JpaRepository<Todos, Long>, TodoQueryDslRepo {

}