package com.yangnjo.dessert_atelier.orders.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.orders.domain.entity.OrderDetail;
import com.yangnjo.dessert_atelier.orders.domain.entity.Refund;
import com.yangnjo.dessert_atelier.orders.domain.entity.RefundStatus;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.RefundOptionProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefundCreateDto {
    private Long orderId;
    private List<String> reason;
    private List<RefundOptionProperty> properties;
    private boolean isContainDeliveryFee;

    public Refund toEntity(OrderDetail orderDetail) {
        return Refund.builder()
                .orderDetail(orderDetail)
                .reason(reason)
                .properties(properties)
                .isContainDeliveryFee(isContainDeliveryFee)
                .status(RefundStatus.REQUEST_REFUND)
                .build();
    }
}
