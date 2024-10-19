package com.yangnjo.dessert_atelier.domain_service.order.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_service.order.OrderQueryService;
import com.yangnjo.dessert_atelier.domain_service.order.exception.OrderNotFoundException;
import com.yangnjo.dessert_atelier.repository.dto.OrderDestinationDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderSimpleDto;
import com.yangnjo.dessert_atelier.repository.query.OrderQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderQueryRepo orderQueryRepo;

    @Override
    public List<OrderSimpleDto> getSimpleOrdersByMemberId(Long memberId, PageOption pageOption,
            PeriodOption periodOption) {
        return orderQueryRepo.findSimpleByMemberId(memberId, pageOption, periodOption);
    }

    @Override
    public OrderDetailDto getOrderDetailByOrderCode(Long orderCode) {
        return orderQueryRepo.findDetailByOrderCode(orderCode).orElseThrow(() -> new OrderNotFoundException());
    }

    @Override
    public OrderDestinationDto getOrderDestinationByOrderCode(Long orderCode) {
        return orderQueryRepo.findDestinationByOrderCode(orderCode).orElseThrow(() -> new OrderNotFoundException());
    }
}
