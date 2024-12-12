package com.yangnjo.dessert_atelier.domain_service.product.dto;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.product.Option;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionCreateDto {
    Long dpId;
    String description;
    Integer price;
    Integer optionLayer;

    public Option toEntity(DisplayProduct dp) {
        return new Option(dp, description, price, optionLayer);
    }
}
