package com.yangnjo.dessert_atelier.orders.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.orders.domain.entity.QRefund;
import com.yangnjo.dessert_atelier.orders.domain.entity.RefundStatus;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.CartOptionProperty;

import lombok.Getter;

@Getter
public class RefundDto {
    private Long id;
    private Long orderId;
    private List<String> reason;
    public List<CartOptionProperty> properties;
    private boolean isContainDeliveryFee;
    private RefundStatus status;

    public static Expression<RefundDto> asDto() {
        QRefund refund = QRefund.refund;
        return Projections.constructor(
                RefundDto.class,
                refund.id,
                refund.orderDetail.id,
                refund.reason,
                refund.properties,
                refund.isContainDeliveryFee,
                refund.status);
    }

    public RefundDto(Long id, Long orderId, List<String> reason, List<CartOptionProperty> properties,
            boolean isContainDeliveryFee, RefundStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.reason = reason;
        this.properties = properties;
        this.isContainDeliveryFee = isContainDeliveryFee;
        this.status = status;
    }

}
