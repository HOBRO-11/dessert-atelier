package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.total.QTotalSaleOption.*;

import java.time.LocalDate;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalSaleOptionGraphDto {

  private Long id;
  private Long optionId;
  private Integer saleAmount;
  private LocalDate createdAt;

  public static Expression<TotalSaleOptionGraphDto> asDto() {
    return Projections.constructor(
        TotalSaleOptionGraphDto.class,
        totalSaleOption.id,
        totalSaleOption.option.id,
        totalSaleOption.saleAmount,
        totalSaleOption.createdAt);
  }

}
