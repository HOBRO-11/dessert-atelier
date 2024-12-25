package com.yangnjo.dessert_atelier.service.product.dto;

import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ProductUpdateForm {
    @Setter
    Long productId;
    Integer price;
    @Setter
    String thumb;

    public ProductUpdateDto toDto() {
        return new ProductUpdateDto(productId, price, thumb);
    }
}
