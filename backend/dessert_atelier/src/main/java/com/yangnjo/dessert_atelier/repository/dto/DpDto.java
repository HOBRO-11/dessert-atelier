package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.display_product.QDisplayProduct.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DpDto {
    private Long id;
    private String naming;
    private String description;
    private String thumb;
    private SaleStatus saleStatus;

    public static Expression<DpDto> asDto() {
        return Projections.constructor(DpDto.class,
                displayProduct.id,
                displayProduct.naming,
                displayProduct.description,
                displayProduct.thumb,
                displayProduct.saleStatus);
    }
}
