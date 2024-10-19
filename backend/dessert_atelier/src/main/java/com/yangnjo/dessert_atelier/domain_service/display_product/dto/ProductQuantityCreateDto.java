package com.yangnjo.dessert_atelier.domain_service.display_product.dto;

import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.display_product.ProductQuantity;
import com.yangnjo.dessert_atelier.domain.product.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductQuantityCreateDto {
  Long productId;
  Long optionId;
  Integer quantity;

  public ProductQuantity toEntity(Product product, Option option) {
    return new ProductQuantity(product, option, quantity);
  }

}
