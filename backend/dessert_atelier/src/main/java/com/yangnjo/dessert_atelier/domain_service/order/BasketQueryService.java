package com.yangnjo.dessert_atelier.domain_service.order;

import com.yangnjo.dessert_atelier.repository.dto.BasketDto;

public interface BasketQueryService {

  BasketDto getBasketByMemberId(Long memberId);

}