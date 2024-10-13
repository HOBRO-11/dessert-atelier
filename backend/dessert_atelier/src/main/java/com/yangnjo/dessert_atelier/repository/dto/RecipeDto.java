package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.product.QRecipe.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.product.ComponentUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeDto {
  private Long id;
  private Long componentId;
  private String componentName;
  private Integer quantity;
  private ComponentUnit unit;

  public static Expression<RecipeDto> asDto() {
    return Projections.constructor(RecipeDto.class,
        recipe.id,
        recipe.component.id,
        recipe.component.name,
        recipe.quantity,
        recipe.component.unit);
  }
}
