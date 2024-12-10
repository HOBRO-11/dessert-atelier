package com.yangnjo.dessert_atelier.domain_service.order.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain_service.order.OrderQueryService;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderDto;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderSimpleDto;
import com.yangnjo.dessert_atelier.repository.order.query.OrderQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderQueryRepo orderQueryRepo;

    @Override
    public Page<OrderSimpleDto> findAllSimpleByOrderStatus(OrderStatus orderStatus, PageOption pageOption,
            PeriodOption periodOption) {
        List<OrderSimpleDto> dtos = orderQueryRepo.findAllSimpleByOrderStatus(orderStatus, pageOption, periodOption);

        if (dtos.size() > pageOption.getSize()) {
            dtos.remove(pageOption.getSize());
        }

        Long count = orderQueryRepo.countAllSimpleByOrderStatus(orderStatus);
        return PageResponse.of(dtos, pageOption, count);
    }

    @Override
    public Optional<OrderDto> getByOrderCode(Long orderCode) {
        return orderQueryRepo.findByOrderCode(orderCode);
    }

    @Override
    public Page<OrderSimpleDto> getSimpleByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption) {
        List<OrderSimpleDto> dtos = orderQueryRepo.findAllSimpleByMemberId(memberId, pageOption, periodOption);

        if (dtos.size() > pageOption.getSize()) {
            dtos.remove(pageOption.getSize());
        }

        Long count = orderQueryRepo.countAllSimpleByMemberId(memberId);
        return PageResponse.of(dtos, pageOption, count);
    }
}
