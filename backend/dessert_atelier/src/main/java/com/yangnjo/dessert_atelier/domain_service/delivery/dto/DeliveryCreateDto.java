package com.yangnjo.dessert_atelier.domain_service.delivery.dto;

import com.yangnjo.dessert_atelier.domain_model.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain_model.order.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryCreateDto {
    Long orderCode;
    String deliveryCode;

    public Delivery toEntity(Orders orders) {
        return new Delivery(deliveryCode, orders);
    }
}
