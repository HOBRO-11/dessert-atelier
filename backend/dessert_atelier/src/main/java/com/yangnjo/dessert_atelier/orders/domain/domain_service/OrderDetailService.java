package com.yangnjo.dessert_atelier.orders.domain.domain_service;

import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderStatus;
import com.yangnjo.dessert_atelier.orders.dto.OrderCreateDto;

public interface OrderDetailService {

    Long create(OrderCreateDto dto);

    void updateAddress(Long orderId, Address address);

    void updateStatus(Long orderId, OrderStatus status);

    void setDeliveryCode(Long orderId, String deliveryCode);

    void minusCheckSum(Long orderId, Integer amountPrice);

}