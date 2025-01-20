package com.yangnjo.dessert_atelier.orders.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderStatus;
import com.yangnjo.dessert_atelier.orders.domain.entity.QOrderDetail;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.CartOptionProperty;

import lombok.Getter;

@Getter
public class OrderDetailDto {

    private Long orderId;
    private Long memberId;
    private String guestEmail;
    private String guestPhone;
    private String deliveryCode;
    private String paymentId;
    private Address address;
    private List<CartOptionProperty> optionProperties;
    private Long totalPrice;
    private Long deliveryFee;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Expression<OrderDetailDto> asDto() {
        QOrderDetail orderDetail = QOrderDetail.orderDetail;
        return Projections.constructor(OrderDetailDto.class,
                orderDetail.id,
                orderDetail.memberId,
                orderDetail.guestEmail,
                orderDetail.guestPhone,
                orderDetail.deliveryCode,
                orderDetail.paymentId,
                orderDetail.address,
                orderDetail.properties,
                orderDetail.totalPrice,
                orderDetail.deliveryFee,
                orderDetail.status,
                orderDetail.createdAt,
                orderDetail.updatedAt);
    }

}
