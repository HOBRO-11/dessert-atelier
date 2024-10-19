package com.yangnjo.dessert_atelier.domain_service.delivery.dto;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryCreateDto {
  String deliveryCode;
  Long deliveryCompanyId;

  public Delivery toEntity(DeliveryCompany deliveryCompany) {
    return new Delivery(deliveryCode, deliveryCompany);
  }
}
