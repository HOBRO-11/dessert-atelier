package com.yangnjo.dessert_atelier.domain_service.delivery;

import com.yangnjo.dessert_atelier.domain.delivery.DeliveryStatus;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateDto;

public interface DeliveryCommandService {

  Long createDelivery(DeliveryCreateDto dto);

  void updateDeliveryStatus(Long deliveryId, DeliveryStatus status);

  void deleteDelivery(Long deliveryId);

  void deleteDelivery(String deliveryCode, Long deliveryCompanyId);

}