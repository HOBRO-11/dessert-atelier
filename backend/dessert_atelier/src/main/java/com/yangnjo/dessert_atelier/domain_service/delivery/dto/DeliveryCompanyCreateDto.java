package com.yangnjo.dessert_atelier.domain_service.delivery.dto;

import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryCompanyCreateDto {
  String deliveryCompanyName;
  String phone;

  public DeliveryCompany toEntity() {
    return new DeliveryCompany(deliveryCompanyName, phone);
  }
}
