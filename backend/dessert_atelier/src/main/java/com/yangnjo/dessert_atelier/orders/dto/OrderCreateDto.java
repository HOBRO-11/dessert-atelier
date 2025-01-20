package com.yangnjo.dessert_atelier.orders.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderDetail;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderStatus;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.OrderOptionProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreateDto {
    Long memberId;
    String guestEmail;
    String guestPhone;
    Address address;
    List<OrderOptionProperty> cartOptionProperty;
    Integer totalPrice;
    Integer deliveryFee;

    public OrderDetail toEntity(Long orderId) {
        return OrderDetail.builder()
                .id(orderId)
                .memberId(memberId)
                .guestEmail(guestEmail)
                .guestPhone(guestPhone)
                .address(address)
                .properties(cartOptionProperty)
                .totalPrice(totalPrice)
                .deliveryFee(deliveryFee)
                .checkSum(totalPrice + deliveryFee)
                .status(OrderStatus.PENDING_PAYMENT)
                .build();
    }

}
