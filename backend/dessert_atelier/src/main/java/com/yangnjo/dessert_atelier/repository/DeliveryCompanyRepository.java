package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;

@Repository
public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Long> {

}
