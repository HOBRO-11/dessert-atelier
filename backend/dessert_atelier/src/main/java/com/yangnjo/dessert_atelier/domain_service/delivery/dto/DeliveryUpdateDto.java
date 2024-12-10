package com.yangnjo.dessert_atelier.domain_service.delivery.dto;

import com.yangnjo.dessert_atelier.domain_model.delivery.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryUpdateDto {
    String deliveryCode;
    DeliveryStatus deliveryStatus;
}
