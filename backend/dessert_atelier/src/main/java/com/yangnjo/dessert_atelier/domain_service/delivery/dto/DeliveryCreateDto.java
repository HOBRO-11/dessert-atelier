package com.yangnjo.dessert_atelier.domain_service.delivery.dto;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;
import com.yangnjo.dessert_atelier.domain.order.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryCreateDto {
    Long orderCode;
    String deliveryCode;
    Long deliveryCompanyId;

    public Delivery toEntity(Orders orders, DeliveryCompany deliveryCompany) {
        return new Delivery(orders, deliveryCode, deliveryCompany);
    }
}
