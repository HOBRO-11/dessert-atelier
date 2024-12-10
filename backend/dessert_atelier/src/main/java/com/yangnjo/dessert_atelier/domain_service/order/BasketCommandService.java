package com.yangnjo.dessert_atelier.domain_service.order;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.order.BasketProperty;

public interface BasketCommandService {

    void addProperties(Long memberId, List<BasketProperty> properties);

    void removeProperty(Long memberId, List<Long> optionIds);

}