package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.delivery.QDeliveryCompany.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryCompanyDto {
  public String deliveryCompanyName;
  public String phone;

  public static Expression<DeliveryCompanyDto> asDto() {
    return Projections.constructor(DeliveryCompanyDto.class, deliveryCompany.companyName, deliveryCompany.phone);
  }
}
