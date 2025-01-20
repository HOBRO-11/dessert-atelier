package com.yangnjo.dessert_atelier.orders.domain.repostiory.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PeriodOption;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderStatus;
import com.yangnjo.dessert_atelier.orders.dto.OrderDetailDto;
import com.yangnjo.dessert_atelier.orders.dto.OrderSimpleDto;

public interface OrderQueryService {

    List<OrderSimpleDto> findAllSimpleByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption);

    Optional<OrderDetailDto> findByOrderId(Long orderId);

    List<OrderSimpleDto> findAllSimpleByOrderStatus(OrderStatus orderStatus, PageOption pageOption,
            PeriodOption periodOption);

    Long countAllSimpleByMemberId(Long memberId);

    Long countAllSimpleByOrderStatus(OrderStatus orderStatus);
}