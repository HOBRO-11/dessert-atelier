package com.yangnjo.dessert_atelier.repository.product.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.product.OptionHeaderType;
import com.yangnjo.dessert_atelier.domain_model.product.QProductOptionHeader;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOptionHeaderDto {
    private Long id;
    private Long displayProductId;
    private String optionHeaderName;
    private OptionHeaderType headerType;

    public static Expression<ProductOptionHeaderDto> asDto() {
        QProductOptionHeader productOptionHeader = QProductOptionHeader.productOptionHeader;
        return Projections.constructor(ProductOptionHeaderDto.class,
                productOptionHeader.id,
                productOptionHeader.displayProduct.id,
                productOptionHeader.optionHeaderName,
                productOptionHeader.headerType);
    }
}
