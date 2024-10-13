package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.delivery.QDelivery.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DeliveryDto {
  private String deliveryCode;
  private DeliveryStatus deliveryStatus;
  private String deliveryCompanyName;


  public static Expression<DeliveryDto> asDto() {
    return Projections.constructor(DeliveryDto.class, delivery.deliveryCode, delivery.deliveryStatus, delivery.deliveryCompany.companyName);
  }
}
