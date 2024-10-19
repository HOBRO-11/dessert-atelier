package com.yangnjo.dessert_atelier.domain_service.delivery;

import com.yangnjo.dessert_atelier.repository.dto.DeliveryDto;

public interface DeliveryQueryService {

  DeliveryDto getByCode(Long deliveryId);

}