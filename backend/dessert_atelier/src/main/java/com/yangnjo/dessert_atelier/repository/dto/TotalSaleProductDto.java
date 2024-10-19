package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.total.QTotalSaleProduct.*;

import java.time.LocalDate;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleProductDto {

  private Long id;
  private Long productId;
  private String productName;
  private Integer saleAmount;
  private LocalDate createdAt;

  public static Expression<TotalSaleProductDto> asDto() {
    return Projections.constructor(
        TotalSaleProductDto.class,
        totalSaleProduct.id,
        totalSaleProduct.product.id,
        totalSaleProduct.product.name,
        totalSaleProduct.saleAmount,
        totalSaleProduct.createdAt);
  }

}
