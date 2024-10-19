package com.yangnjo.dessert_atelier.domain_service.delivery;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.DeliveryCompanyDto;

public interface DeliveryCompanyQueryService {

  DeliveryCompanyDto getById(Long deliveryCompanyId);

  List<DeliveryCompanyDto> getAll();

}