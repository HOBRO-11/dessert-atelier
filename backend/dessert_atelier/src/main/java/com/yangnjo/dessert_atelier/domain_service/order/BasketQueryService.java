package com.yangnjo.dessert_atelier.domain_service.order;

import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.order.dto.BasketDto;

public interface BasketQueryService {

    Optional<BasketDto> getByMemberId(Long memberId);

}