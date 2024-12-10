package com.yangnjo.dessert_atelier.repository.delivery.dto;

import java.time.LocalDateTime;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.delivery.DeliveryStatus;
import com.yangnjo.dessert_atelier.domain_model.delivery.QDelivery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryDto {
    private String deliveryCode;
    private Long orderCode;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime createdAt;

    public static Expression<DeliveryDto> asDto() {
        QDelivery delivery = QDelivery.delivery;
        return Projections.constructor(DeliveryDto.class, delivery.deliveryCode, delivery.deliveryStatus,
                delivery.createdAt);
    }
}
