package com.yangnjo.dessert_atelier.orders.dto;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.orders.domain.entity.QCartOption;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.CartOptionProperty;

import lombok.Getter;

@Getter
public class CartOptionDto {
    private List<CartOptionProperty> properties;
    private List<CartOptionPropertyDto> propertyDto;

    public static Expression<CartOptionDto> asDto() {
        QCartOption cartOption = QCartOption.cartOption;
        return Projections.constructor(
                CartOptionDto.class,
                cartOption.properties);
    }

    public void addOrderPropertyDtos(CartOptionProperty property, String title, String thumbnail,
            List<String> explanations, Integer optionPrice) {
        propertyDto = new ArrayList<>();
        propertyDto.add(new CartOptionPropertyDto(property.getSalePageId(), title, thumbnail, property.getOptionIds(),
                explanations, property.getQuantity(), optionPrice));
    }

    public CartOptionDto(List<CartOptionProperty> properties) {
        this.properties = properties;
    }

}
