package com.yangnjo.dessert_atelier.repository.product.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QDisplayProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DpSimpleDto {
    private Long id;
    private String title;
    private String thumb;
    private DisplayProductStatus dpStatus;

    public static Expression<DpSimpleDto> asDto() {
        QDisplayProduct displayProduct = QDisplayProduct.displayProduct;
        return Projections.constructor(DpSimpleDto.class,
                displayProduct.id,
                displayProduct.title,
                displayProduct.thumb,
                displayProduct.displayProductStatus);
    }
}
