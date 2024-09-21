package com.yangnjo.dessert_atelier.common.dto.product;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.db.entity.Products;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetailDto {

    private Long id;

    private String name;

    private Integer price;

    private Integer quantity;

    private ProductStatus status = ProductStatus.SALE;

    private String thumb;

    private String comment;

    private List<String> images = new ArrayList<>();

    public static ProductDetailDto toDto(Products products) {
        return new ProductDetailDto(products.getId(), products.getName(), products.getPrice(), products.getQuantity(),
                products.getStatus(), products.getThumb(), products.getComment(), products.getImages().getImagesUrl());
    }
}
