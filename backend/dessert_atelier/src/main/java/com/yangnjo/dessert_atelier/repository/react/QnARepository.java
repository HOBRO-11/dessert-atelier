package com.yangnjo.dessert_atelier.repository.react;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.react.QnA;

@Repository
public interface QnARepository extends JpaRepository<QnA, Long> {

}
