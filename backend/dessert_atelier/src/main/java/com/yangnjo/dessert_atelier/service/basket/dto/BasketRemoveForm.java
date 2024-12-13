package com.yangnjo.dessert_atelier.service.basket.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.order.BasketProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasketRemoveForm {

    private List<BasketProperty> basketProperties;

}
