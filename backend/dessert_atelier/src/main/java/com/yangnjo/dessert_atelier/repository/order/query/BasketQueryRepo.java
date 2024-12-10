package com.yangnjo.dessert_atelier.repository.order.query;

import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.order.dto.BasketDto;

public interface BasketQueryRepo {

    Optional<BasketDto> findByMemberId(Long memberId);

}