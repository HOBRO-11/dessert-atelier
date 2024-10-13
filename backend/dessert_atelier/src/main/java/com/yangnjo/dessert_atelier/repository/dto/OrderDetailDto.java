package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.order.QOptionQuantity.*;
import static com.yangnjo.dessert_atelier.domain.order.QOrders.*;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.order.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class OrderDetailDto {

  private String orderCode;
  private Long deliveryId;
  private OrderStatus orderStatus;
  private Long totalPrice;
  private LocalDateTime createdAt;
  @Setter
  private List<OrderPropertyDto> orderPropertyDtos;

  /*
   * 반드시 setOrderPropertyDtos 을 사용하여 DTO 완성을 마무리 짓자.
   * @see orderPropertyDtos
   */
  public OrderDetailDto(String orderCode, Long deliveryId, OrderStatus orderStatus, Long totalPrice,
      LocalDateTime createdAt) {
    this.orderCode = orderCode;
    this.deliveryId = deliveryId;
    this.orderStatus = orderStatus;
    this.totalPrice = totalPrice;
    this.createdAt = createdAt;
  }

  public static Expression<OrderDetailDto> asIncompleteDto() {
    return Projections.constructor(OrderDetailDto.class,
        orders.orderCode,
        orders.delivery.id,
        orders.orderStatus,
        orders.totalPrice,
        orders.createdAt);
  }

  @Getter
  @ToString
  public static class OrderPropertyDto {
    private String title;
    private List<String> optionDescriptions;
    private Integer quantity;

    public OrderPropertyDto(String title, List<String> optionDescriptions, Integer quantity) {
      this.title = title;
      this.optionDescriptions = optionDescriptions;
      this.quantity = quantity;
    }

    public static Expression<OrderPropertyDto> asDto() {
      return Projections.constructor(OrderPropertyDto.class,
          optionQuantity.displayProductPreset.title,
          optionQuantity.optionIds,
          optionQuantity.quantity);
    }

  }

}
