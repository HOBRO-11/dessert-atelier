package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.product.QProduct.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDto {
  private Long id;
  private String name;
  private Integer price;
  private String thumb;
  private ProductStatus productStatus;

  public static Expression<ProductDto> asDto() {
    return Projections.constructor(ProductDto.class,
        product.id,
        product.name,
        product.price,
        product.thumb,
        product.productStatus);
  }
}
