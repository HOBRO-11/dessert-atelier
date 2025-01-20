package com.yangnjo.dessert_atelier.orders.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.yangnjo.dessert_atelier.orders.domain.entity.QRefund;
import com.yangnjo.dessert_atelier.orders.domain.entity.RefundStatus;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.CartOptionProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefundSimpleDto {
    private static final String SALE_PAGE_ID_PATH = "properties->0->>'salePageId'";

    private Long id;
    private Long orderId;
    private Long salePageId;
    private Integer optionPropertiesCount;
    public List<CartOptionProperty> properties;
    private boolean isContainDeliveryFee;
    private RefundStatus status;

    public static Expression<RefundSimpleDto> asDto() {
        NumberPath<Long> salePageIdPath = Expressions.numberPath(Long.class, SALE_PAGE_ID_PATH);
        QRefund refund = QRefund.refund;
        return Projections.constructor(
                RefundSimpleDto.class,
                refund.id,
                refund.orderDetail.id,
                salePageIdPath,
                refund.properties.size(),
                refund.properties,
                refund.isContainDeliveryFee,
                refund.status);
    }

}
