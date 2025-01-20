package com.yangnjo.dessert_atelier.orders.dto;

import java.time.LocalDateTime;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderStatus;
import com.yangnjo.dessert_atelier.orders.domain.entity.QOrderDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderSimpleDto {

    private static final String SALE_PAGE_ID_PATH = "properties->0->>'salePageId'";

    private Long orderId;
    private Long memberId;
    private String guestEmail;
    private String guestPhone;
    private Long salePageId;
    private Integer optionPropertiesCount;
    private Long totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Expression<OrderSimpleDto> asIncompleteDto() {
        QOrderDetail orderDetail = QOrderDetail.orderDetail;
        NumberPath<Long> salePageIdPath = Expressions.numberPath(Long.class, SALE_PAGE_ID_PATH);
        
        return Projections.constructor(OrderSimpleDto.class,
                orderDetail.id,
                orderDetail.memberId,
                orderDetail.guestEmail,
                orderDetail.guestPhone,
                salePageIdPath,
                orderDetail.properties.size(),
                orderDetail.totalPrice,
                orderDetail.status,
                orderDetail.createdAt,
                orderDetail.updatedAt);
    }

}
