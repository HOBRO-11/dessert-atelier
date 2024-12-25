package com.yangnjo.dessert_atelier.domain_service.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductUpdateDto {
    Long productId;
    Integer price;
    String thumb;
}
