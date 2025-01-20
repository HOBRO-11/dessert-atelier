package com.yangnjo.dessert_atelier.sale.dto;

import com.yangnjo.dessert_atelier.sale.domain.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCreateDto {
    String name;
    Integer price;
    String thumbnail;
    Integer stockQuantity;

    public Product toEntity() {
        return Product.builder().name(name).price(price).thumbnail(thumbnail).stockQuantity(stockQuantity).build();
    }
}
