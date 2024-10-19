package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.order.QOrders.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class OrderDestinationDto {
  private Long orderCode;
  private Long memberId;
  private String postCode;
  private String detailAddress;
  private String receiver;
  private String phone;

  public static Expression<OrderDestinationDto> asDto() {
    return Projections.constructor(OrderDestinationDto.class,
        orders.orderCode,
        orders.member.id,
        orders.destination.postCode,
        orders.destination.detailAddress,
        orders.destination.receiver,
        orders.destination.phone);
  }
}
