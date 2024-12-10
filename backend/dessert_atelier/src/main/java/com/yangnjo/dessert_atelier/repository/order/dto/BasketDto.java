package com.yangnjo.dessert_atelier.repository.order.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.order.BasketProperty;
import com.yangnjo.dessert_atelier.domain_model.order.QBasket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasketDto {
    private Long id;
    private Long memberId;
    private List<BasketProperty> properties;

    public static Expression<BasketDto> asDto() {
        QBasket basket = QBasket.basket;
        return Projections.constructor(
                BasketDto.class,
                basket.id,
                basket.member.id,
                basket.properties);
    }
}
