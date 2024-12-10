package com.yangnjo.dessert_atelier.domain_service.order.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.order.OrderedOption;
import com.yangnjo.dessert_atelier.domain_model.order.Orders;
import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreateDto {
    Long memberId;
    String guestPhone;
    Destination destination;
    Long totalPrice;
    Long deliveryFee;
    List<OrderedOption> orderedOptions; 

    public Orders toUserOrderEntity(Long orderCode, Member member) {
        return Orders.createUserOrder(orderCode, member, destination, totalPrice, deliveryFee, orderedOptions);
    }

    public Orders toGuestOrderEntity(Long orderCode) {
        return Orders.createGuestOrder(orderCode, guestPhone, destination, totalPrice, deliveryFee, orderedOptions);
    }

}
