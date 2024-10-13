package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.display_product.QOption.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionSimpleDto {
  private Long id;
  private Integer totalQuantity;
  private Integer optionLayer;
  private OptionStatus optionStatus;
  private String description;
  private Integer price;

  public static Expression<OptionSimpleDto> asDto() {
    return Projections.constructor(OptionSimpleDto.class,
        option.id,
        option.totalQuantity,
        option.optionLayer,
        option.optionStatus,
        option.description,
        option.price);
  }
}
