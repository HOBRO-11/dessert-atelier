package com.yangnjo.dessert_atelier.repository.order.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.order.OrderStatus;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderDto;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderSimpleDto;

public interface OrderQueryRepo {

    List<OrderSimpleDto> findAllSimpleByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption);

    Optional<OrderDto> findByOrderCode(Long orderCode);

    List<OrderSimpleDto> findAllSimpleByOrderStatus(OrderStatus orderStatus, PageOption pageOption,
            PeriodOption periodOption);

    Long countAllSimpleByMemberId(Long memberId);

    Long countAllSimpleByOrderStatus(OrderStatus orderStatus);
}