package com.yangnjo.dessert_atelier.domain_service.delivery.dto;

import com.yangnjo.dessert_atelier.domain.delivery.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryUpdateDto {
    String deliveryCode;
    Long deliveryCompanyId;
    DeliveryStatus deliveryStatus;
}
