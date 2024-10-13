package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.order.QBasket.*;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.order.BasketProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class BasketDto {
  private List<BasketProperty> properties;

  public static Expression<BasketDto> asDto(){
    return Projections.constructor(BasketDto.class, basket.properties);
    
  }
}
