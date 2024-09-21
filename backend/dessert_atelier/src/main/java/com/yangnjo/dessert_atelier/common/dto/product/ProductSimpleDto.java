package com.yangnjo.dessert_atelier.common.dto.product;

import com.yangnjo.dessert_atelier.db.entity.Products;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSimpleDto {

    private Long id;

    private String name;

    private int price;

    private String thumb;

    private ProductStatus productStatus;

    public static ProductSimpleDto toDto(Products products) {
        return new ProductSimpleDto(products.getId(), products.getName(), products.getPrice(), products.getThumb(), products.getStatus());
    }
}
