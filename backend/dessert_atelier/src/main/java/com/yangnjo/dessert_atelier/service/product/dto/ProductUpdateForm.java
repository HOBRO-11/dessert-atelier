package com.yangnjo.dessert_atelier.service.product.dto;

import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductUpdateForm {
    Long productId;
    String name;
    Integer price;
    String thumb;

    public ProductUpdateDto toDto(){
        return new ProductUpdateDto(productId, name, price, thumb);
    }
}
