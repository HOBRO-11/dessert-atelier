package com.yangnjo.dessert_atelier.domain_service.order;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.order.BasketProperty;

public interface BasketCommandService {

  Long createBasket(Long memberId);

  void addProperties(Long memberId, List<BasketProperty> properties);

  void removeProperty(Long memberId, Long dppId, List<Long> optionIds);

}