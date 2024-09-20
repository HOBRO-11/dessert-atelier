package com.yangnjo.dessert_atelier.common.dto.product;

import com.yangnjo.dessert_atelier.db.model.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductUpdateDto {

    private String name;

    private Integer price;

    private ProductStatus status = ProductStatus.SALE;

    private String thumb;
}
