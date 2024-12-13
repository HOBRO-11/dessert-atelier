package com.yangnjo.dessert_atelier.service.basket.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.order.BasketProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasketAddForm {

    private Long displayProductId;
    private Integer optionLayer;
    private List<BasketProperty> basketProperties;
}
