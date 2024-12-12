package com.yangnjo.dessert_atelier.repository.product.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QOption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionSimpleDto {
    private Long id;
    private Long displayProductId;
    private Integer optionLayer;
    private OptionStatus optionStatus;
    private String description;
    private Integer price;

    public static Expression<OptionSimpleDto> asDto() {
        QOption option = QOption.option;
        return Projections.constructor(OptionSimpleDto.class,
                option.id,
                option.optionLayer,
                option.optionStatus,
                option.description,
                option.price);
    }
}
