package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.product.QComponent.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.product.ComponentUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComponentDto {
  private Long id;
  private String name;
  private ComponentUnit unit;

  public static Expression<ComponentDto> asDto() {
    return Projections.constructor(ComponentDto.class,
        component.id,
        component.name,
        component.unit);
  }

}
