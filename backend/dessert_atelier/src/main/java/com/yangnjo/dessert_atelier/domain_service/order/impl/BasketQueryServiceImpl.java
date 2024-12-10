package com.yangnjo.dessert_atelier.domain_service.order.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.order.BasketQueryService;
import com.yangnjo.dessert_atelier.repository.order.dto.BasketDto;
import com.yangnjo.dessert_atelier.repository.order.query.BasketQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketQueryServiceImpl implements BasketQueryService {

    private final BasketQueryRepo basketQueryRepo;

    @Override
    public Optional<BasketDto> getByMemberId(Long memberId) {
        return basketQueryRepo.findByMemberId(memberId);
    }
}
