package com.yangnjo.dessert_atelier.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.db.entity.DeliveryCompany;

@Repository
public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Long> {

}
