package com.yangnjo.dessert_atelier.domain_service.order.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.order.BasketQueryService;
import com.yangnjo.dessert_atelier.domain_service.order.exception.BasketNotFoundException;
import com.yangnjo.dessert_atelier.repository.dto.BasketDto;
import com.yangnjo.dessert_atelier.repository.query.BasketQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketQueryServiceImpl implements BasketQueryService {

    private final BasketQueryRepo basketQueryRepo;

    @Override
    public BasketDto getBasketByMemberId(Long memberId) {
        return basketQueryRepo.findByMemberId(memberId).orElseThrow(() -> new BasketNotFoundException());
    }
}
