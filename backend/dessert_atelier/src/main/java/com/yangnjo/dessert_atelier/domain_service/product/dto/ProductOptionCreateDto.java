package com.yangnjo.dessert_atelier.domain_service.product.dto;

import com.yangnjo.dessert_atelier.domain_model.product.ProductOption;
import com.yangnjo.dessert_atelier.domain_model.product.ProductOptionHeader;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOptionCreateDto {
    Long optionHeaderId;
    String description;
    Integer price;

    public ProductOption toEntity(ProductOptionHeader oh) {
        return new ProductOption(oh, description, price);
    }
}
