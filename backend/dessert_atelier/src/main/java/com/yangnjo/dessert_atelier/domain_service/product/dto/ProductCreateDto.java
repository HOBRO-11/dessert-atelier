package com.yangnjo.dessert_atelier.domain_service.product.dto;

import com.yangnjo.dessert_atelier.domain_model.product.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCreateDto {
    String name;
    Integer price;
    String thumb;
    Integer quantity;

    public Product toEntity() {
        return new Product(name, price, thumb, quantity);
    }
}
