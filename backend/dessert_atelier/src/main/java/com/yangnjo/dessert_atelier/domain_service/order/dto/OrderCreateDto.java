package com.yangnjo.dessert_atelier.domain_service.order.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.order.Orders;
import com.yangnjo.dessert_atelier.domain_service.dto.DestinationDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//FIXME: 잘못 만들었음
@Getter
@AllArgsConstructor
public class OrderCreateDto {
    Long memberId;
    String guestPhone;
    DestinationDto destinationDto;
    List<Long> optionIds;
    Long totalPrice;
    Long deliveryFee;

    public Orders toUserOrderEntity(Long orderCode, Member member) {
        return Orders.createUserOrder(orderCode, member, destinationDto.toDestination(), totalPrice, deliveryFee);
    }

    public Orders toGuestOrderEntity(Long orderCode) {
        return Orders.createGuestOrder(orderCode, guestPhone, destinationDto.toDestination(), totalPrice, deliveryFee);
    }

}
