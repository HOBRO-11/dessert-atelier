package com.yangnjo.dessert_atelier.orders.domain.repostiory.query;

import java.util.Optional;

import com.yangnjo.dessert_atelier.orders.dto.CartOptionDto;

public interface CartOptionQueryService {

    Optional<CartOptionDto> findByMemberId(Long memberId);

}