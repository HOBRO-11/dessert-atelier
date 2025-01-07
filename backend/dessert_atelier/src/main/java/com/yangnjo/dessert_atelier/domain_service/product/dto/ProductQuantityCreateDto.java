package com.yangnjo.dessert_atelier.domain_service.product.dto;

import com.yangnjo.dessert_atelier.domain_model.product.ProductOption;
import com.yangnjo.dessert_atelier.domain_model.product.Product;
import com.yangnjo.dessert_atelier.domain_model.product.ProductQuantity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductQuantityCreateDto {
    Long productId;
    Long optionId;
    Integer quantity;

    public ProductQuantity toEntity(Product product, ProductOption option) {
        return new ProductQuantity(product, option, quantity);
    }

}
