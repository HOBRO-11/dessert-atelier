package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.total.QTotalSaleProduct.*;

import java.time.LocalDate;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleProductGraphDto {

  private Long id;
  private Long productId;
  private Integer saleAmount;
  private LocalDate createdAt;

  public static Expression<TotalSaleProductGraphDto> asDto() {
    return Projections.constructor(
        TotalSaleProductGraphDto.class,
        totalSaleProduct.id,
        totalSaleProduct.product.id,
        totalSaleProduct.saleAmount,
        totalSaleProduct.createdAt);
  }

}
