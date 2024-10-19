package com.yangnjo.dessert_atelier.domain_service.product.dto;

import com.yangnjo.dessert_atelier.domain.product.Product;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCreateDto {
  String name;
  Integer price;
  String thumb;
  ProductStatus status;

  public Product toEntity() {
    return new Product(name, price, thumb, status);
  }
}
