package com.yangnjo.dessert_atelier.repository.product.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QProductOption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOptionSimpleDto {
    private Long id;
    private Long optionHeaderId;
    private OptionStatus optionStatus;
    private String description;
    private Integer price;

    public static Expression<ProductOptionSimpleDto> asDto() {
        QProductOption productOption = QProductOption.productOption;
        return Projections.constructor(ProductOptionSimpleDto.class,
                productOption.id,
                productOption.productOptionHeader.id,
                productOption.optionStatus,
                productOption.description,
                productOption.price);
    }
}
