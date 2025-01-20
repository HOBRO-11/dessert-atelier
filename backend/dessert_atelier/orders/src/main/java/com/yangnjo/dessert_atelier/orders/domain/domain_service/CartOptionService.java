package com.yangnjo.dessert_atelier.orders.domain.domain_service;

import java.util.List;

import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.CartOptionProperty;

public interface CartOptionService {

    void addProperties(Long memberId, List<CartOptionProperty> properties);

    void removeProperty(Long memberId, List<CartOptionProperty> properties);

}