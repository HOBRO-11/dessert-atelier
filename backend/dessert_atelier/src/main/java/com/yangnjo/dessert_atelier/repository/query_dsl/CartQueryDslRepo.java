package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.dto.BasketCartDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderCartDto;

public interface CartQueryDslRepo {

    Optional<OrderCartDto> searchOrderCartWithCondition(Long cartId);

    Optional<BasketCartDto> searchBasketCartWithCondition(Long cartId);

}