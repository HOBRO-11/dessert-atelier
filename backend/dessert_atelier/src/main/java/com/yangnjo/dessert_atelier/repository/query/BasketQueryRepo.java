package com.yangnjo.dessert_atelier.repository.query;

import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.dto.BasketDto;

public interface BasketQueryRepo {

  Optional<BasketDto> findByMemberId(Long memberId);

}