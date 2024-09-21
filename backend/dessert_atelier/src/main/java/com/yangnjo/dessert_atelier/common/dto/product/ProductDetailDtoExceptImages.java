package com.yangnjo.dessert_atelier.common.dto.product;

import com.yangnjo.dessert_atelier.db.model.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetailDtoExceptImages {

    private Long id;

    private String name;

    private Integer price;

    private Integer quantity;

    private ProductStatus status = ProductStatus.SALE;

    private String thumb;

    private String comment;

}
