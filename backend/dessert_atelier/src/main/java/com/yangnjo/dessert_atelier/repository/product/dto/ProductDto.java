package com.yangnjo.dessert_atelier.repository.product.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.product.QProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Integer price;
    private String thumb;
    private Integer quantity;

    public static Expression<ProductDto> asDto() {
        QProduct product = QProduct.product;
        return Projections.constructor(ProductDto.class,
                product.id,
                product.name,
                product.price,
                product.thumb,
                product.quantity);
    }
}
