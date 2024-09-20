package com.yangnjo.dessert_atelier.common.dto.product;

import com.yangnjo.dessert_atelier.db.entity.Products;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private int price;

    private String thumb;

    public static ProductDto toDto(Products products) {
        return new ProductDto(products.getId(), products.getName(), products.getPrice(), products.getThumb());
    }
}
