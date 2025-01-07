package com.yangnjo.dessert_atelier.domain_service.product.dto;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.product.ProductOptionHeader;
import com.yangnjo.dessert_atelier.domain_model.product.OptionHeaderType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOptionHeaderCreateDto {
    String optionHeaderName;
    OptionHeaderType headerType;

    public ProductOptionHeader toEntity(DisplayProduct dp) {
        return new ProductOptionHeader(dp, optionHeaderName, headerType);
    }
}
