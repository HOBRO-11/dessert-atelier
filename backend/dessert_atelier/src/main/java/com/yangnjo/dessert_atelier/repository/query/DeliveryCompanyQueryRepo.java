package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.dto.DeliveryCompanyDto;

public interface DeliveryCompanyQueryRepo {

  List<DeliveryCompanyDto> findAll();

  Optional<DeliveryCompanyDto> find(Long deliveryCompanyId);
}