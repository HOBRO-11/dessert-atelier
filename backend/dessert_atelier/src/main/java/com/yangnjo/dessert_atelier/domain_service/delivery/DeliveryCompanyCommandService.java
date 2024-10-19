package com.yangnjo.dessert_atelier.domain_service.delivery;

import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCompanyCreateDto;

public interface DeliveryCompanyCommandService {

  Long createDeliveryCompany(DeliveryCompanyCreateDto dto);

  void updateDeliveryCompanyPhone(Long deliveryCompanyId, String phone);

  void deleteDeliveryCompany(Long deliveryCompanyId);

}