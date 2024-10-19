package com.yangnjo.dessert_atelier.domain_service.order;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain_service.order.dto.OrderCreateDto;

public interface OrderCommandService {

  Long createUserOrder(OrderCreateDto dto);

  Long createGuestOrder(OrderCreateDto dto);

  void updateOrderStatus(Long orderCode, OrderStatus status);

  void setDelivery(Long orderCode, Delivery delivery);

}