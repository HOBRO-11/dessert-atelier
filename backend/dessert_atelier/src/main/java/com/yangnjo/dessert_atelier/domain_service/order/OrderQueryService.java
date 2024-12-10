package com.yangnjo.dessert_atelier.domain_service.order;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.order.OrderStatus;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderDto;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderSimpleDto;

public interface OrderQueryService {

    Page<OrderSimpleDto> getSimpleByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption);

    Optional<OrderDto> getByOrderCode(Long orderCode);

    Page<OrderSimpleDto> findAllSimpleByOrderStatus(OrderStatus orderStatus, PageOption pageOption,
            PeriodOption periodOption);
}