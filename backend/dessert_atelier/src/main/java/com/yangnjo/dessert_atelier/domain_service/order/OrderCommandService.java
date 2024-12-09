package com.yangnjo.dessert_atelier.domain_service.order;

import com.yangnjo.dessert_atelier.domain.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain_service.order.dto.OrderCreateDto;

public interface OrderCommandService {

    Long createMemberOrder(OrderCreateDto dto);

    Long createGuestOrder(OrderCreateDto dto);

    void updateMemberOrderStatus(Long orderCode, Long memberId, OrderStatus status);

    public void updateGuestOrderStatus(Long orderCode, String guestPhone, OrderStatus status);
}