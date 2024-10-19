package com.yangnjo.dessert_atelier.domain_service.order.dto;

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.order.Orders;
import com.yangnjo.dessert_atelier.domain.value_type.Destination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreateDto {
  Long memberId;
  String password;
  Destination destination;
  Long totalPrice;

  public Orders toUserOrderEntity(Long orderCode, Member member) {
    return Orders.createUserOrder(orderCode, member, destination, totalPrice);
  }

  public Orders toGuestOrderEntity(Long orderCode) {
    return Orders.createGuestOrder(orderCode, password, destination, totalPrice);
  }
}
