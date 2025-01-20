package com.yangnjo.dessert_atelier.sale.dto;

import com.yangnjo.dessert_atelier.sale.domain.entity.Product;
import com.yangnjo.dessert_atelier.sale.domain.entity.ProductQuantity;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductQuantityCreateDto {
    Long productId;
    Long saleOptionId;
    Integer quantity;

    public ProductQuantity toEntity(Product product, SaleOption option) {
        return ProductQuantity.builder()
                .saleOption(option)
                .product(product)
                .quantity(quantity).build();
    }

}
